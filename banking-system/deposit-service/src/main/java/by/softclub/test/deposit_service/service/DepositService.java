package by.softclub.test.deposit_service.service;

import by.softclub.test.deposit_service.dto.DepositClientDto;
import by.softclub.test.deposit_service.dto.DepositClientStatus;
import by.softclub.test.deposit_service.dto.InterestCalculationResponseDto;
import by.softclub.test.deposit_service.dto.Operation.OperationResponseDto;
import by.softclub.test.deposit_service.dto.StatementResponseDto;
import by.softclub.test.deposit_service.dto.deposit.DepositRequestDto;
import by.softclub.test.deposit_service.dto.deposit.DepositResponseDto;
import by.softclub.test.deposit_service.dto.deposit.DepositUpdateDto;
import by.softclub.test.deposit_service.entity.*;
import by.softclub.test.deposit_service.exception.*;
import by.softclub.test.deposit_service.mapper.DepositMapper;
import by.softclub.test.deposit_service.mapper.OperationMapper;
import by.softclub.test.deposit_service.repository.DepositRepository;
import by.softclub.test.deposit_service.repository.DepositTypeRepository;
import by.softclub.test.deposit_service.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final OperationRepository operationRepository;
    private final DepositTypeRepository depositTypeRepository;
    private final DepositMapper depositMapper;
    private final OperationMapper operationMapper;
    private final ClientService clientService;
    private final OperationService operationService;

    @Autowired
    public DepositService(DepositRepository depositRepository,
                          OperationRepository operationRepository, DepositTypeRepository depositTypeRepository,
                          DepositMapper depositMapper, OperationMapper operationMapper,
                          ClientService clientService, OperationService operationService) {
        this.depositRepository = depositRepository;
        this.operationRepository = operationRepository;
        this.depositTypeRepository = depositTypeRepository;
        this.operationMapper = operationMapper;
        this.clientService = clientService;
        this.depositMapper = depositMapper;
        this.operationService = operationService;
    }

    // Создание нового депозитного договора
    public DepositResponseDto createDeposit(DepositRequestDto requestDto) {
        DepositClientDto client = clientService.getClientByPassport(requestDto.getPassportSeries(), requestDto.getPassportNumber());

        if (!isEligibleForDeposit(client))
            throw new ClientNotEligibleException("Client is not eligible for deposit");

        validateUniqueContractNumber(requestDto.getContractNumber());

        DepositType depositType = depositTypeRepository.findByName(requestDto.getDepositTypeName())
                .orElseThrow(() -> new DepositTypeNotFoundException("Deposit type not found: " + requestDto.getDepositTypeName()));

        Deposit deposit = depositMapper.toEntity(requestDto);
        deposit.setClientId(client.getId());
        deposit.setDepositType(depositType);
        deposit.setConclusionDate(LocalDate.now());
        deposit.setEndDate(deposit.getConclusionDate().plusMonths(requestDto.getContractTerm()));
        deposit.setStatus(DepositStatus.ACTIVE);
        return depositMapper.toDto(depositRepository.save(deposit));
    }

    public boolean isEligibleForDeposit(DepositClientDto clientDto) {

        if (!clientDto.getStatus().equals(DepositClientStatus.ACTIVE)) {
            throw new ClientNotEligibleException("Client is not active. Current status: " + clientDto.getStatus());
        }

        if (!isClientOldEnough(clientDto.getBirthDate())) {
            throw new ClientNotEligibleException("Client is under the minimum age requirement");
        }

        return true;
    }

    private boolean isClientOldEnough(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        int age = now.getYear() - birthDate.getYear();
        if (now.getMonthValue() < birthDate.getMonthValue() ||
                (now.getMonthValue() == birthDate.getMonthValue() && now.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }
        return age >= 25;
    }

    // Получение депозитного договора по ID
    public DepositResponseDto getDepositById(Long id) {
        Deposit deposit = findDepositById(id);
        return depositMapper.toDto(deposit);
    }

    // Обновление депозитного договора
    public DepositResponseDto updateDeposit(Long id, DepositUpdateDto updateDto) {
        Deposit deposit = findDepositById(id);

        if (updateDto.getContractAmount() != null) {
            deposit.setContractAmount(updateDto.getContractAmount());
        }
        if (updateDto.getInterestRate() != null) {
            deposit.setInterestRate(updateDto.getInterestRate());
        }
        if (updateDto.getContractTerm() != null) {
            deposit.setContractTerm(updateDto.getContractTerm());
            deposit.setEndDate(deposit.getConclusionDate().plusMonths(updateDto.getContractTerm()));
        }
        if (updateDto.getStatus() != null) {
            deposit.setStatus(updateDto.getStatus());
        }
        if (updateDto.getStatus() != null) {
            deposit.setStatus(updateDto.getStatus());
        }

        return depositMapper.toDto(depositRepository.save(deposit));
    }

    // Удаление депозитного договора
    public void deleteDeposit(Long id) {
        Deposit deposit = findDepositById(id);
        depositRepository.delete(deposit);
    }

    // Пополнение депозита
    public OperationResponseDto topUpDeposit(Long id, BigDecimal amount) {
        Deposit deposit = findDepositById(id);
        validateTopUpAllowed(deposit);

        deposit.setContractAmount(deposit.getContractAmount().add(amount));
        depositRepository.save(deposit);

        Operation operation = createOperation(deposit, OperationType.DEPOSIT, amount);
        return operationMapper.toDto(operation);
    }

    // Частичное снятие средств с депозита
    public OperationResponseDto withdrawFromDeposit(Long id, BigDecimal amount) {
        Deposit deposit = findDepositById(id);
        validatePartialWithdrawalAllowed(deposit, amount);

        deposit.setContractAmount(deposit.getContractAmount().subtract(amount));
        depositRepository.save(deposit);

        Operation operation = createOperation(deposit, OperationType.WITHDRAWAL, amount);
        return operationMapper.toDto(operation);
    }

    // Закрытие депозитного договора
    public void closeDeposit(Long id) {
        Deposit deposit = findDepositById(id);

        if (deposit.getStatus() == DepositStatus.CLOSED) {
            throw new OperationNotAllowedException("Deposit is already closed");
        }

        deposit.setStatus(DepositStatus.CLOSED);
        depositRepository.save(deposit);

        Operation operation = createOperation(deposit, OperationType.CLOSURE, BigDecimal.ZERO);
        operationRepository.save(operation);
    }

    // Создание операции
    private Operation createOperation(Deposit deposit, OperationType type, BigDecimal amount) {
        Operation operation = new Operation();
        operation.setDeposit(deposit);
        operation.setOperationType(type);
        operation.setAmount(amount);
        operation.setOperationDate(LocalDateTime.now());
        return operationRepository.save(operation);
    }

    // Проверка уникальности номера договора
    private void validateUniqueContractNumber(String contractNumber) {
        if (depositRepository.existsByContractNumber(contractNumber)) {
            throw new ContractNumberExistsException("Contract number " + contractNumber + " already exists");
        }
    }

    // Проверка возможности пополнения
    private void validateTopUpAllowed(Deposit deposit) {
        if (!deposit.getDepositType().getCondition().getIsTopUpAllowed()) {
            throw new OperationNotAllowedException("Top-up is not allowed for this deposit");
        }
    }

    // Проверка возможности частичного снятия
    private void validatePartialWithdrawalAllowed(Deposit deposit, BigDecimal amount) {
        if (!deposit.getDepositType().getCondition().getIsPartialWithdrawalAllowed()) {
            throw new OperationNotAllowedException("Partial withdrawal is not allowed for this deposit");
        }
        if (deposit.getContractAmount().subtract(amount).compareTo(deposit.getDepositType().getCondition().getMinimumBalance()) < 0) {
            throw new OperationNotAllowedException("The remaining balance cannot be less than the minimum balance");
        }
    }

    // Поиск депозита по ID
    private Deposit findDepositById(Long id) {
        return depositRepository.findById(id)
                .orElseThrow(() -> new DepositNotFoundException("Deposit not found with ID: " + id));
    }

    public List<DepositResponseDto> getAllDeposits() {
        List<Deposit> clients = depositRepository.findAll();
        return clients.stream()
                .map(depositMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Начисление процентов за один месяц
     */
    public void accrueMonthlyInterest(Long depositId, BigDecimal monthlyInterest) {
        Deposit deposit = findDepositById(depositId);
        deposit.setContractAmount(deposit.getContractAmount().add(monthlyInterest));
        createOperation(deposit, OperationType.INTEREST_PAYMENT, monthlyInterest);
        depositRepository.save(deposit);
    }

    /**
     * Расчет общей прибыли и ежемесячного баланса
     */
    public InterestCalculationResponseDto calculateTotalInterestAndMonthlyBalances(Long depositId) {
        Deposit deposit = findDepositById(depositId);

        BigDecimal initialAmount = deposit.getContractAmount();
        BigDecimal interestRate = deposit.getInterestRate();
        int contractTerm = deposit.getContractTerm();

        BigDecimal totalInterestEarned = calculateCompoundInterest(initialAmount, interestRate, contractTerm);

        List<InterestCalculationResponseDto.MonthlyBalanceDto> monthlyBalances = calculateMonthlyBalances(initialAmount, interestRate, deposit.getConclusionDate(), contractTerm);

        BigDecimal finalBalance = initialAmount.add(totalInterestEarned);

        return new InterestCalculationResponseDto(
                initialAmount.setScale(2, RoundingMode.HALF_UP),
                finalBalance.setScale(2, RoundingMode.HALF_UP),
                totalInterestEarned.setScale(2, RoundingMode.HALF_UP),
                monthlyBalances
        );
    }

    private BigDecimal calculateCompoundInterest(BigDecimal principal, BigDecimal annualRate, int months) {
        BigDecimal rate = annualRate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal compoundedAmount = principal.multiply(BigDecimal.ONE.add(monthlyRate).pow(months));
        return compoundedAmount.subtract(principal).setScale(2, RoundingMode.HALF_UP);
    }

    public List<InterestCalculationResponseDto.MonthlyBalanceDto> calculateMonthlyBalances(BigDecimal initialAmount, BigDecimal annualRate, LocalDate startDate, int months) {
        List<InterestCalculationResponseDto.MonthlyBalanceDto> monthlyBalances = new ArrayList<>();
        BigDecimal currentBalance = initialAmount;

        for (int month = 1; month <= months; month++) {
            int daysInMonth = startDate.lengthOfMonth();
            BigDecimal monthlyInterest = currentBalance
                    .multiply(annualRate)
                    .multiply(BigDecimal.valueOf(daysInMonth))
                    .divide(BigDecimal.valueOf(365).multiply(BigDecimal.valueOf(100)), 2, RoundingMode.HALF_UP);

            currentBalance = currentBalance.add(monthlyInterest).setScale(2, RoundingMode.HALF_UP);
            monthlyBalances.add(new InterestCalculationResponseDto.MonthlyBalanceDto(month, currentBalance));
            startDate = startDate.plusMonths(1);
        }

        return monthlyBalances;
    }

    public List<OperationResponseDto> getOperationsByDepositId(Long id) {
        return operationRepository.findByDepositId(id).stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }

    // Формирование выписки по депозитному договору
    public List<OperationResponseDto> getDepositStatement(Long id) {
        Deposit deposit = findDepositById(id);
        return deposit.getOperations().stream()
                .map(operationMapper::toDto)
                .toList();
    }

    /**
     * Формирование выписки по договору за период
     */
    public StatementResponseDto generateStatement(Long depositId, LocalDateTime startDate, LocalDateTime endDate) {
        Deposit deposit = findDepositById(depositId);
        List<Operation> operations = operationService.getOperationsByPeriod(depositId, startDate, endDate);

        BigDecimal totalDeposits = calculateTotalByType(operations, OperationType.DEPOSIT);
        BigDecimal totalWithdrawals = calculateTotalByType(operations, OperationType.WITHDRAWAL);
        BigDecimal totalInterestEarned = calculateTotalByType(operations, OperationType.INTEREST_PAYMENT);

        BigDecimal initialBalance = calculateInitialBalance(deposit, startDate);
        BigDecimal finalBalance = calculateFinalBalance(initialBalance, operations);

        return new StatementResponseDto(
                deposit.getContractNumber(),
                startDate,
                endDate,
                initialBalance,
                finalBalance,
                operationService.mapToDtos(operations),
                totalDeposits,
                totalWithdrawals,
                totalInterestEarned
        );
    }

    private BigDecimal calculateTotalByType(List<Operation> operations, OperationType type) {
        return operations.stream()
                .filter(op -> op.getOperationType() == type)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateInitialBalance(Deposit deposit, LocalDateTime startDate) {
        List<Operation> allOperations = operationService.getAllOperationsBefore(deposit.getId(), startDate);
        return calculateFinalBalance(deposit.getContractAmount(), allOperations);
    }

    private BigDecimal calculateFinalBalance(BigDecimal initialBalance, List<Operation> operations) {
        return operations.stream()
                .map(op -> {
                    if (op.getOperationType() == OperationType.DEPOSIT || op.getOperationType() == OperationType.INTEREST_PAYMENT) {
                        return op.getAmount();
                    } else if (op.getOperationType() == OperationType.WITHDRAWAL) {
                        return op.getAmount().negate();
                    }
                    return BigDecimal.ZERO;
                })
                .reduce(initialBalance, BigDecimal::add);
    }

}