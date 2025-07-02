package by.softclub.test.deposit_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DepositClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private DepositClientStatus status;

}
