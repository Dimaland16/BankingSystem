package by.softclub.test.deposit_service.dto.deposit;

import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionUpdateDto;
import by.softclub.test.deposit_service.entity.DepositStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DepositUpdateDto {

    private BigDecimal contractAmount;

    private BigDecimal interestRate;

    private Integer contractTerm;

    private DepositStatus status;

    private DepositConditionUpdateDto conditions;

}