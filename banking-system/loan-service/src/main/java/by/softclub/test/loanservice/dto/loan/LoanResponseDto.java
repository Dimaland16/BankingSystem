package by.softclub.test.loanservice.dto.loan;

import by.softclub.test.loanservice.dto.payment.PaymentHistoryDto;
import by.softclub.test.loanservice.dto.payment.PaymentScheduleDto;
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
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDto {

    @NotNull(message = "ID is required")
    private Long loanId;

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotBlank(message = "Contract number is required")
    @Size(max = 50, message = "Contract number must not exceed 50 characters")
    private String contractNumber;

    @NotBlank(message = "Product type is required")
    @Size(max = 50, message = "Product type must not exceed 50 characters")
    private LoanProductType productType;

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be greater than 0")
    private BigDecimal loanAmount;

    @NotNull(message = "Interest rate is required")
    @PositiveOrZero(message = "Interest rate must be non-negative")
    private BigDecimal interestRate;

    @NotNull(message = "Loan term is required")
    @Min(value = 1, message = "Loan term must be at least 1 month")
    private Integer loanTermMonths;

    @NotNull(message = "Contract date is required")
    @PastOrPresent(message = "Contract date must be in the past or present")
    private LocalDate contractDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDate endDate;

    @NotNull(message = "Status is required")
    @Valid
    private LoanStatus status;

    @Valid
    private List<PaymentScheduleDto> paymentSchedule;

    @Valid
    private List<PaymentHistoryDto> paymentHistory;

    @PositiveOrZero(message = "Current debt must be non-negative")
    private BigDecimal currentDebt;

    @NotNull(message = "Loan terms are required")
    @Valid
    private LoanTermsDto loanTerms;
}