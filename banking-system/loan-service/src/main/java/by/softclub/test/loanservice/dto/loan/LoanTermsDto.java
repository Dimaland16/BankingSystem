package by.softclub.test.loanservice.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanTermsDto {

    private Boolean earlyRepaymentAllowed;

    private String penaltyConditions;

    //тут дни, а в monthlyPayment месяца !!!
    private Integer gracePeriodDays;

    private BigDecimal monthlyPayment;
}