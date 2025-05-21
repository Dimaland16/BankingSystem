package by.softclub.test.loanservice.dto.loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDto {


    @NotBlank(message = "Contract number is required")
    @Size(max = 50, message = "Contract number must not exceed 50 characters")
    private String contractNumber;

    @NotBlank(message = "Product type is required")
    @Size(max = 50, message = "Product type must not exceed 50 characters")
    private String productType;

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be positive")
    private BigDecimal loanAmount;

    @NotNull(message = "Interest rate is required")
    @Positive(message = "Interest rate must be positive")
    private BigDecimal interestRate;

    @NotNull(message = "Loan term months is required")
    @Min(value = 1, message = "Loan term months must be at least 1")
    private Integer loanTermMonths;

    @NotNull(message = "Contract date is required")
    @PastOrPresent(message = "Contract date must be in the past or present")
    private LocalDate contractDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "Contract date must be in the future or present")
    private LocalDate endDate;

    @NotNull(message = "Loan terms are required")
    @Valid
    private LoanTermsDto loanTerms;
}