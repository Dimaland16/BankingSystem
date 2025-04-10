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
public class PassportData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String series;
    private String number;
    private LocalDate issueDate;
    private String issuer;
    private String code;

    @OneToOne(mappedBy = "passportData")
    private Client client;

}
