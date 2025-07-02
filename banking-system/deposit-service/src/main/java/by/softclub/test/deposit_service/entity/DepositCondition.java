package by.softclub.test.deposit_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposit_conditions")
public class DepositCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "deposit_type_id", nullable = false)
    private DepositType depositType;

    private Boolean isTopUpAllowed;

    private Boolean isPartialWithdrawalAllowed;

    private BigDecimal minimumBalance;

    @Enumerated(EnumType.STRING)
    private InterestPaymentFrequency interestPaymentFrequency;


}
