package by.softclub.test.clientservice.dto.client;

import by.softclub.test.clientservice.dto.address.AddressUpdateDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoUpdateDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataUpdateDto;
import by.softclub.test.clientservice.entity.ClientStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientUpdateDto {

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Size(max = 50, message = "Middle name must not exceed 50 characters")
    private String middleName;

    private LocalDate birthDate;

    private ClientStatus status;

    @Valid
    private PassportDataUpdateDto passportDataUpdateDto;

    @Valid
    private ContactInfoUpdateDto contactInfoUpdateDto;

    @Valid
    private AddressUpdateDto addressUpdateDto;
}
