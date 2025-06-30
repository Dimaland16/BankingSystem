package by.softclub.test.loanservice.dto.payment;

import by.softclub.test.loanservice.entity.PaymentHistoryStatus;
import by.softclub.test.loanservice.entity.PaymentType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryDto {

    private Long id;

    @Column(name = "loan_id")
    private Long loanId;

    @NotNull(message = "Payment date is required")
    private LocalDateTime paymentDate;

    @NotNull(message = "Amount paid is required")
    @Positive(message = "Amount paid must be positive")
    private BigDecimal amountPaid;

    @NotBlank(message = "Payment type is required")
    private PaymentType paymentType;

    @NotBlank(message = "Payment history status is required")
    private PaymentHistoryStatus status;
}