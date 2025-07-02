package by.softclub.test.deposit_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class InterestCalculationResponseDto {

    private BigDecimal startBalance;
    private BigDecimal finalBalance;
    private BigDecimal totalInterestEarned;
    private List<MonthlyBalanceDto> monthlyBalances;

    @Data
    @AllArgsConstructor
    public static class MonthlyBalanceDto {
        private int month;
        private BigDecimal balance;
    }
}