package by.softclub.test.loanservice.dto.loan;

import by.softclub.test.loanservice.entity.LoanProductType;
import by.softclub.test.loanservice.entity.LoanStatus;
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
public class LoanUpdateDto {

    @NotBlank(message = "Contract number is required")
    @Size(max = 50, message = "Contract number must not exceed 50 characters")
    private String contractNumber;

    @NotNull(message = "Product type must not be null")
    //@Size(max = 50, message = "Product type must not exceed 50 characters")
    //Supported types are(for @Size):
    //CharSequence (length of character sequence is evaluated)
    //Collection (collection size is evaluated)
    //Map (map size is evaluated)
    //Array (array length is evaluated
    private LoanProductType productType;

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be positive")
    private BigDecimal loanAmount;

    @NotNull(message = "Interest rate is required")
    @Positive(message = "Interest rate must be positive")
    private BigDecimal interestRate;

    @NotNull(message = "Loan term is required")
    @Min(value = 1, message = "Loan term must be at least 1 month")
    private Integer loanTermMonths;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDate endDate;

    @NotNull(message = "Status is required")
    @Valid
    private LoanStatus status;

    @NotNull(message = "Current debt is required")
    @PositiveOrZero(message = "Current debt must be positive")
    private BigDecimal currentDebt;

    @NotNull(message = "Loan terms are required")
    @Valid
    private LoanTermsDto loanTerms;
}