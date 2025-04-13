package by.softclub.test.loanservice.dto.loan;

import by.softclub.test.loanservice.dto.payment.PaymentHistoryDto;
import by.softclub.test.loanservice.dto.payment.PaymentScheduleDto;
import by.softclub.test.loanservice.entity.LoanStatus;
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

    private Long id;

    private Long clientId;

    private String contractNumber;

    private String productType;

    private BigDecimal loanAmount;

    private BigDecimal interestRate;

    private Integer loanTermMonths;

    private LocalDate contractDate;

    private LocalDate endDate;

    private LoanStatus status;

    private List<PaymentScheduleDto> paymentSchedule;

    private List<PaymentHistoryDto> paymentHistory;

    private BigDecimal currentDebt;

    private LoanTermsDto loanTerms;
}