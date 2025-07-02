package by.softclub.test.deposit_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    @Column(unique = true)
    private String contractNumber;

    @ManyToOne
    @JoinColumn(name = "deposit_type_id", nullable = false)
    private DepositType depositType;

    private BigDecimal contractAmount;

    private BigDecimal interestRate;

    private Integer contractTerm;

    private LocalDate conclusionDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private DepositStatus status;

    @OneToMany(mappedBy = "deposit", cascade = CascadeType.ALL)
    private List<Operation> operations;

}