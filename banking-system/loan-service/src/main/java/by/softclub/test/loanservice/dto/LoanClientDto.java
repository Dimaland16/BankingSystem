package by.softclub.test.loanservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private LoanClientStatus status;

}
