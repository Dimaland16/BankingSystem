package by.softclub.test.deposit_service.dto.deposit;

import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDto {
    @NotNull
    private String passportSeries;

    @NotNull
    private String passportNumber;

    @NotNull
    private String contractNumber;

    @NotNull
    private String depositTypeName;

    @NotNull
    private BigDecimal contractAmount;

    @NotNull
    private BigDecimal interestRate;

    @NotNull
    private Integer contractTerm;

}