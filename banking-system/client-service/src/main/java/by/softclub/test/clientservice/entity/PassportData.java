package by.softclub.test.clientservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passport_data")
public class PassportData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "series")
    private String series;
    @Column(name = "number")
    private String number;
    @Column(name = "issue_date")
    private LocalDate issueDate;
    @Column(name = "issuer")
    private String issuer;
    @Column(name = "code")
    private String code;

    @OneToOne(mappedBy = "passportData")
    private Client client;

}
