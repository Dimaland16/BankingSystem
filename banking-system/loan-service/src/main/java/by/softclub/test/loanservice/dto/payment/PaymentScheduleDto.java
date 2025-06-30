package by.softclub.test.loanservice.dto.payment;

import by.softclub.test.loanservice.entity.PaymentStatus;
import jakarta.persistence.Column;
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
public class PaymentScheduleDto {

    private Long id;

    @Column(name = "loan_id")
    private Long loanId;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotNull(message = "Principal amount is required")
    @PositiveOrZero(message = "Principal amount must be positive or zero")
    private BigDecimal principalAmount;

    @NotNull(message = "Interest amount is required")
    @PositiveOrZero(message = "Interest amount must be positive or zero")
    private BigDecimal interestAmount;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be greater than zero")
    private BigDecimal totalAmount;

    @NotBlank(message = "Payment status is required")
    private PaymentStatus status;
}