package by.softclub.test.loanservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanTerms {

    @Column(name = "early_repayment_allowed")
    private Boolean earlyRepaymentAllowed;

    @Column(name = "penalty_conditions")
    private String penaltyConditions;

    @Column(name = "grace_period_days")
    private Integer gracePeriodDays;

    @Column(name = "monthly_payment")
    private Integer monthlyPayment;

    public LoanTerms(Boolean earlyRepaymentAllowed, String penaltyConditions, Integer gracePeriodDays) {
        this.earlyRepaymentAllowed = earlyRepaymentAllowed;
        this.penaltyConditions = penaltyConditions;
        this.gracePeriodDays = gracePeriodDays;
    }

    //public LoanTerms() {}
}
