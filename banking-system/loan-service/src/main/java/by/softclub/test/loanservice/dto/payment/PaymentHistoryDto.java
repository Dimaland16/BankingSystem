package by.softclub.test.loanservice.dto.payment;

import by.softclub.test.loanservice.entity.PaymentHistoryStatus;
import by.softclub.test.loanservice.entity.PaymentType;
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

    private LocalDateTime paymentDate;

    private BigDecimal amountPaid;

    private PaymentType paymentType;

    private PaymentHistoryStatus status;
}