package by.softclub.test.loanservice.entity;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public class LoanTerms {

    @Column(name = "early_repayment_allowed")
    private Boolean earlyRepaymentAllowed;

    @Column(name = "penalty_conditions")
    private String penaltyConditions;

    @Column(name = "grace_period_days")
    private Integer gracePeriodDays;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

}
