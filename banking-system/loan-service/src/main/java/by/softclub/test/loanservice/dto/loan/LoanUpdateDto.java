package by.softclub.test.loanservice.dto.loan;

import by.softclub.test.loanservice.entity.LoanStatus;
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

    private String contractNumber;

    private String productType;

    private BigDecimal loanAmount;

    private BigDecimal interestRate;

    private Integer loanTermMonths;

    private LocalDate endDate;

    private LoanStatus status;

    private BigDecimal currentDebt;

    private LoanTermsDto loanTerms;
}