package by.softclub.test.loanservice.dto.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Early repayment allowed is required")
    private Boolean earlyRepaymentAllowed;

    @NotBlank(message = "Penalty conditions are required")
    @Size(max = 1500, message = "Penalty conditions must not exceed 1500 characters")
    private String penaltyConditions;

    //тут дни, а в monthlyPayment месяца !!!
    @NotNull(message = "Grace period days is required")
    @Positive(message = "Grace period days must be positive")
    private Integer gracePeriodDays;

    @NotNull(message = "Monthly payment is required")
    @Positive(message = "Monthly payment must be positive")
    private Integer monthlyPayment;
}