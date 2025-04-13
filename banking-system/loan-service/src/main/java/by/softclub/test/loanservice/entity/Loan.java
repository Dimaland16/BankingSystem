package by.softclub.test.loanservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loanId")
    private long loanId;

    @Column(name = "client_id")
    private long clientId;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "product_type")
    private LoanProductType productType;

    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "loan_term")
    private Integer loanTermMonths;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private List<PaymentSchedule> paymentSchedule;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private List<PaymentHistory> paymentHistory;

    @Column(name = "current_debt")
    private BigDecimal currentDebt;

    @Embedded
    private LoanTerms loanTerms;
}
