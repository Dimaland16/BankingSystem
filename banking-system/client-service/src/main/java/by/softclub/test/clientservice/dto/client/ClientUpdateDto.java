package by.softclub.test.clientservice.dto.client;

import by.softclub.test.clientservice.dto.address.AddressDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataDto;
import by.softclub.test.clientservice.entity.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDto {

    private String firstName;

    private String lastName;

    private String middleName;

    private LocalDate birthDate;

    private ClientStatus status;

    private PassportDataDto passportData;

    private ContactInfoDto contactInfo;

    private AddressDto address;
}
