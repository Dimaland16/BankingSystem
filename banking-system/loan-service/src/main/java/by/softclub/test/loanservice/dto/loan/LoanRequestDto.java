package by.softclub.test.loanservice.dto.loan;

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

    private Long clientId;

    private String contractNumber;

    private String productType;

    private BigDecimal loanAmount;

    private BigDecimal interestRate;

    private Integer loanTermMonths;

    private LocalDate contractDate;

    private LocalDate endDate;

    private LoanTermsDto loanTerms;
}