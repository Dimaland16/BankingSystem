package by.softclub.test.deposit_service.controller;

import by.softclub.test.deposit_service.dto.InterestCalculationResponseDto;
import by.softclub.test.deposit_service.dto.Operation.OperationResponseDto;
import by.softclub.test.deposit_service.dto.StatementResponseDto;
import by.softclub.test.deposit_service.dto.deposit.DepositRequestDto;
import by.softclub.test.deposit_service.dto.deposit.DepositResponseDto;
import by.softclub.test.deposit_service.dto.deposit.DepositUpdateDto;
import by.softclub.test.deposit_service.service.DepositService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/deposits")
public class DepositController {

    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping
    public ResponseEntity<DepositResponseDto> createDeposit(@Valid @RequestBody DepositRequestDto requestDto) {
        return ResponseEntity.ok(depositService.createDeposit(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepositResponseDto> getDepositById(@PathVariable Long id) {
        return ResponseEntity.ok(depositService.getDepositById(id));
    }

    @GetMapping
    public ResponseEntity<List<DepositResponseDto>> getAllDeposits() {
        return ResponseEntity.ok(depositService.getAllDeposits());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepositResponseDto> updateDeposit(
            @PathVariable Long id,
            @Valid @RequestBody DepositUpdateDto updateDto) {
        return ResponseEntity.ok(depositService.updateDeposit(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Long id) {
        depositService.deleteDeposit(id);
        return ResponseEntity.noContent().build();
    }

    // Пополнение депозита
    @PostMapping("/{id}/replenish")
    public ResponseEntity<OperationResponseDto> topUpDeposit(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(depositService.topUpDeposit(id, amount));
    }

    // Частичное снятие средств с депозита
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<OperationResponseDto> withdrawFromDeposit(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(depositService.withdrawFromDeposit(id, amount));
    }

    // Закрытие депозитного договора
    @PostMapping("/{id}/close")
    public ResponseEntity<Void> closeDeposit(@PathVariable Long id) {
        depositService.closeDeposit(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/accrue-interest")
    public ResponseEntity<Void> accrueMonthlyInterest(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        depositService.accrueMonthlyInterest(id, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/interest-calculation")
    public ResponseEntity<InterestCalculationResponseDto> calculateTotalInterestAndMonthlyBalances(@PathVariable Long id) {
        InterestCalculationResponseDto result = depositService.calculateTotalInterestAndMonthlyBalances(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<OperationResponseDto>> getOperations(@PathVariable Long id) {
        List<OperationResponseDto> history = depositService.getOperationsByDepositId(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}/statement")
    public ResponseEntity<StatementResponseDto> generateStatement(
            @PathVariable Long id,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        StatementResponseDto statement = depositService.generateStatement(id, startDate, endDate);
        return ResponseEntity.ok(statement);
    }
}
