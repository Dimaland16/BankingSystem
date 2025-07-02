package by.softclub.test.loanservice.controller;

import by.softclub.test.loanservice.dto.loan.*;
import by.softclub.test.loanservice.service.LoanService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Котроллер кредитных договоров", description = "CRUD операции и операции по работе с кредитными договорами")
@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(
            summary = "Создание нового кредитного договора",
            description = "Создание нового кредитного договора описание"
    )

    @PostMapping
    public ResponseEntity<LoanResponseDto> createLoan(@Valid @RequestBody LoanRequestDto LoanRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.createLoan(LoanRequestDto));
    }

    @Operation(
            summary = "Получить кредитный договор по ID",
            description = "Возвращает полную информацию по кредитному договору по его уникальному идентификатору"
    )
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(
        @PathVariable
        @Parameter(description = "Уникальный ID кредита", example = "1") Long id) {

        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @Operation(
            summary = "Получить все кредитные договоры",
            description = "Возвращает список всех активных кредитных договоров")
    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @Operation(
            summary = "Удалить кредитный договор по ID",
            description = "Удаляет кредитный договор"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable @RequestParam("id") @Parameter(description = "Идентификатор кредитного договора, который удалить", example = "1") Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновить кредитный договор",
            description = "Обновляет существующий кредитный договор"
    )
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDto> updateLoan(
            @PathVariable
            @Parameter(description = "Идентификатор кредитного договора для обновления", example = "1") Long id,
            @Valid @RequestBody LoanUpdateDto updateDto) {
        return ResponseEntity.ok(loanService.updateLoan(id, updateDto));
    }

    @Operation(
            summary = "Сформировать выписку по кредитному договору",
            description = "Формирует текстовое описание выписки по кредитному договору"
    )
    @GetMapping("/{id}/statement")
    public ResponseEntity<String> generateStatement(
            @PathVariable
            @Parameter(description = "Идентификатор кредитного договора для формирования выписки", example = "1") Long id) {
        return ResponseEntity.ok(loanService.generateStatement(id));
    }

    @Operation(
            summary = "Активировать кредит (выдать заем)",
            description = "Переводит кредит в статус ACTIVE после проверки условий договора"
    )
    @PostMapping("/{id}/issue")
    public ResponseEntity<Void> issueLoan(
            @PathVariable
            @Parameter(description = "Идентификатор кредитного договора для активации", example = "1") Long id)
    {
        loanService.issueLoan(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обработать платеж по кредитному договору",
            description = "Принимает платёж по кредитному договору и обновляет баланс"
    )
    @PostMapping("/{id}/payment")
    public ResponseEntity<Void> processPayment(
            @PathVariable
            @Parameter(description = "Идентификатор кредитного договора для оплаты", example = "1") Long id,
            @RequestParam
            @Parameter(description = "Сумма платежа", example = "100.00") BigDecimal amount)
    {
        loanService.processPayment(id, amount);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Закрыть кредитный договор",
            description = "Завершает кредитный договор (например, если он погашён полностью)"
    )
    @PostMapping("/{id}/close")
    public ResponseEntity<Void> closeLoan(
            @PathVariable
            @Parameter(description = "Идентификатор кредитного договора для закрытия", example = "1") Long id)
    {
        loanService.closeLoan(id);
        return ResponseEntity.ok().build();
    }
}
