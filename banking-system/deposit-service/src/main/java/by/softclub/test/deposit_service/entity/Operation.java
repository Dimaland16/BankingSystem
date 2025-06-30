package by.softclub.test.deposit_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deposit_id")
    private Deposit deposit;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private BigDecimal amount;

    private LocalDateTime operationDate;

}