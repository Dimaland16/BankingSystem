package by.softclub.test.deposit_service.dto.depositCondition;

import by.softclub.test.deposit_service.entity.InterestPaymentFrequency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositConditionResponseDto {
    private Long id;

    private Boolean isTopUpAllowed;

    private Boolean isPartialWithdrawalAllowed;

    private BigDecimal minimumBalance;

    private InterestPaymentFrequency interestPaymentFrequency;

}