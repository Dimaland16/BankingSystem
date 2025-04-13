package by.softclub.test.loanservice.dto.payment;

import by.softclub.test.loanservice.entity.PaymentStatus;
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

    private LocalDate paymentDate;

    private BigDecimal principalAmount;

    private BigDecimal interestAmount;

    private BigDecimal totalAmount;

    private PaymentStatus status;
}