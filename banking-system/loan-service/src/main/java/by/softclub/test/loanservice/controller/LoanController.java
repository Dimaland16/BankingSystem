package by.softclub.test.loanservice.controller;

import by.softclub.test.loanservice.dto.loan.*;
import by.softclub.test.loanservice.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // Создание нового кредитного договора
    @PostMapping
    public ResponseEntity<LoanResponseDto> createLoan(@Valid @RequestBody LoanRequestDto LoanRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.createLoan(LoanRequestDto));
    }

    // Получение кредитного договора по ID
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    // Удаление кредитного договора
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.ok().build();
    }
    // Обновление кредитного договора
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDto> updateLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanUpdateDto updateDto) {
        return ResponseEntity.ok(loanService.updateLoan(id, updateDto));
    }

    // Формирование выписки по кредитному договору
    @GetMapping("/{id}/statement")
    public ResponseEntity<String> generateStatement(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.generateStatement(id));
    }

    // Выдача кредита (активация договора)
    @PostMapping("/{id}/issue")
    public ResponseEntity<Void> issueLoan(@PathVariable Long id) {
        loanService.issueLoan(id);
        return ResponseEntity.ok().build();
    }

    // Прием платежей по кредитному договору
    @PostMapping("/{id}/payment")
    public ResponseEntity<Void> processPayment(@PathVariable Long id, @RequestParam BigDecimal amount) {
        loanService.processPayment(id, amount);
        return ResponseEntity.ok().build();
    }

    // Закрытие кредитного договора
    @PostMapping("/{id}/close")
    public ResponseEntity<Void> closeLoan(@PathVariable Long id) {
        loanService.closeLoan(id);
        return ResponseEntity.ok().build();
    }
}
