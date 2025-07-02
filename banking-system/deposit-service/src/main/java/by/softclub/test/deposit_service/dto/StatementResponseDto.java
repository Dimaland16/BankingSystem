package by.softclub.test.deposit_service.dto;

import by.softclub.test.deposit_service.dto.Operation.OperationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class StatementResponseDto {

    private String contractNumber; // Номер договора
    private LocalDateTime startDate; // Дата начала периода
    private LocalDateTime endDate; // Дата окончания периода
    private BigDecimal initialBalance; // Баланс на начало периода
    private BigDecimal finalBalance; // Баланс на конец периода
    private List<OperationResponseDto> operations; // Список операций
    private BigDecimal totalDeposits; // Общая сумма пополнений
    private BigDecimal totalWithdrawals; // Общая сумма снятий
    private BigDecimal totalInterestEarned; // Общая сумма начисленных процентов

}
