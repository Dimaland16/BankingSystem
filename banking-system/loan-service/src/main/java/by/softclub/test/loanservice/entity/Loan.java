package by.softclub.test.loanservice.entity;

import by.softclub.test.loanservice.converter.LoanStatusAttributeConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data // Заменяет @Getter + @Setter + toString() + equals() + hashCode()
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "contract_number", nullable = false, unique = true)
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
    @Convert(converter = LoanStatusAttributeConverter.class)
    @Column(name = "status", nullable = false)
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

    // Конструкторы

    public Loan() {
    }

    public Loan(Long clientId, String contractNumber, LoanProductType productType,
                BigDecimal loanAmount, BigDecimal interestRate, Integer loanTermMonths,
                LocalDate contractDate, LocalDate endDate, LoanStatus status,
                List<PaymentSchedule> paymentSchedule, List<PaymentHistory> paymentHistory,
                BigDecimal currentDebt, LoanTerms loanTerms) {
        this.clientId = clientId;
        this.contractNumber = contractNumber;
        this.productType = productType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTermMonths = loanTermMonths;
        this.contractDate = contractDate;
        this.endDate = endDate;
        this.status = status;
        this.paymentSchedule = paymentSchedule;
        this.paymentHistory = paymentHistory;
        this.currentDebt = currentDebt;
        this.loanTerms = loanTerms;
    }
}