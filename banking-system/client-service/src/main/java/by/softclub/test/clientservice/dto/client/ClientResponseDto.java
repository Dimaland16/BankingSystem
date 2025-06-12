package by.softclub.test.clientservice.dto.client;

import by.softclub.test.clientservice.dto.address.AddressRequestDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoRequestDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataRequestDto;
import by.softclub.test.clientservice.entity.ClientStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String password;

    private LocalDate birthDate;

    private ClientStatus status;

    private LocalDateTime registrationDate;

    private PassportDataRequestDto passportData;

    private ContactInfoRequestDto contactInfo;

    private AddressRequestDto address;
}
