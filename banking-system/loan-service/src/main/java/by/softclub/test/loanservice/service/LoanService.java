package by.softclub.test.loanservice.service;

import by.softclub.test.loanservice.dto.loan.LoanRequestDto;
import by.softclub.test.loanservice.dto.loan.LoanResponseDto;
import by.softclub.test.loanservice.dto.loan.LoanUpdateDto;
import by.softclub.test.loanservice.entity.Loan;
import by.softclub.test.loanservice.entity.LoanStatus;
import by.softclub.test.loanservice.entity.PaymentSchedule;
import by.softclub.test.loanservice.entity.PaymentStatus;
import by.softclub.test.loanservice.mapper.LoanMapper;
import by.softclub.test.loanservice.repository.LoanRepository;
import org.springframework.stereotype.Service;
import java.math.RoundingMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    //добавить интеграцию с клиент-сервис

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    private List<PaymentSchedule> calculateAnnuityPaymentSchedule(Loan loan) {
        List<PaymentSchedule> schedule = new ArrayList<>();

        BigDecimal principal = loan.getLoanAmount();
        BigDecimal annualInterestRate = loan.getInterestRate(); // например, 12%
        int termInMonths = loan.getLoanTermMonths();
        LocalDate startDate = loan.getContractDate();

        // Месячная процентная ставка: annualInterestRate / 100 / 12
        BigDecimal monthlyRate = annualInterestRate.divide(BigDecimal.valueOf(1200), SCALE + 2, ROUNDING_MODE);

        // Расчет аннуитетного платежа
        BigDecimal annuityRatio = monthlyRate.multiply(
                BigDecimal.valueOf(Math.pow(1 + monthlyRate.doubleValue(), termInMonths))
        ).divide(
                BigDecimal.valueOf(Math.pow(1 + monthlyRate.doubleValue(), termInMonths)).subtract(BigDecimal.ONE),
                SCALE + 2,
                ROUNDING_MODE
        );

        BigDecimal monthlyPayment = principal.multiply(annuityRatio).setScale(SCALE, ROUNDING_MODE);

        BigDecimal remainingPrincipal = principal;

        for (int month = 1; month <= termInMonths; month++) {
            LocalDate paymentDate = startDate.plusMonths(month);

            BigDecimal interestPayment = remainingPrincipal.multiply(monthlyRate).setScale(SCALE, ROUNDING_MODE);
            BigDecimal principalPayment = monthlyPayment.subtract(interestPayment).setScale(SCALE, ROUNDING_MODE);

            if (remainingPrincipal.compareTo(principalPayment) < 0) {
                principalPayment = remainingPrincipal;
            }

            remainingPrincipal = remainingPrincipal.subtract(principalPayment).setScale(SCALE, ROUNDING_MODE);

            PaymentSchedule paymentSchedule = new PaymentSchedule();
            paymentSchedule.setPaymentDate(paymentDate);
            paymentSchedule.setPrincipalAmount(principalPayment);
            paymentSchedule.setInterestAmount(interestPayment);
            paymentSchedule.setTotalAmount(monthlyPayment);
            paymentSchedule.setStatus(PaymentStatus.PENDING);

            schedule.add(paymentSchedule);
        }

        return schedule;
    }

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    public LoanResponseDto createLoan(LoanRequestDto requestDto) {
        Loan loan = loanMapper.toEntity(requestDto);
        loan.setStatus(LoanStatus.CREATED);

        // Расчет графика платежей
        loan.setPaymentSchedule(calculateAnnuityPaymentSchedule(loan));

        Loan savedLoan = loanRepository.save(loan);
        return loanMapper.toDto(savedLoan);
    }

    public LoanResponseDto getLoanById(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        return loanMapper.toDto(loan);
    }

    public List<LoanResponseDto> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(loanMapper::toDto)
                .collect(Collectors.toList());
    }

    public LoanResponseDto updateLoan(Long id, LoanUpdateDto updateDto) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));

        // Обновляем поля, если они переданы в DTO
        if (updateDto.getContractNumber() != null) {
            loan.setContractNumber(updateDto.getContractNumber());
        }
        if (updateDto.getProductType() != null) {
            loan.setProductType(updateDto.getProductType());
        }
        if (updateDto.getLoanAmount() != null) {
            loan.setLoanAmount(updateDto.getLoanAmount());
        }
        if (updateDto.getInterestRate() != null) {
            loan.setInterestRate(updateDto.getInterestRate());
        }
        if (updateDto.getLoanTermMonths() != null) {
            loan.setLoanTermMonths(updateDto.getLoanTermMonths());
        }
        if (updateDto.getEndDate() != null) {
            loan.setEndDate(updateDto.getEndDate());
        }
        if (updateDto.getCurrentDebt() != null) {
            loan.setCurrentDebt(updateDto.getCurrentDebt());
        }
/*        if (updateDto.getLoanTerms() != null) {
            loan.setLoanTerms(loanMapper.loanTermsDtoToEntity(updateDto.getLoanTerms()));
        }*/

        Loan updatedLoan = loanRepository.save(loan);
        return loanMapper.toDto(updatedLoan);
    }

    public void deleteLoan(Long id) {
        
    }

    public String generateStatement(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        return "Loan Statement for Contract Number: " + loan.getContractNumber() +
                "\nCurrent Debt: " + loan.getCurrentDebt() +
                "\nStatus: " + loan.getStatus();
    }

    public void issueLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        if (!loan.getStatus().equals(LoanStatus.ACTIVE)) {
            loan.setStatus(LoanStatus.ACTIVE);
            loanRepository.save(loan);
        } else {
            throw new RuntimeException("Loan is already active");
        }
    }

    public void processPayment(Long id, BigDecimal amount) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }
        if (loan.getCurrentDebt().compareTo(amount) < 0) {
            throw new RuntimeException("Payment amount exceeds current debt");
        }
        loan.setCurrentDebt(loan.getCurrentDebt().subtract(amount));
        loanRepository.save(loan);
    }

    public void closeLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        if (loan.getCurrentDebt().compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Cannot close loan with outstanding debt");
        }
        loan.setStatus(LoanStatus.CLOSED);
        loanRepository.save(loan);
    }


}
