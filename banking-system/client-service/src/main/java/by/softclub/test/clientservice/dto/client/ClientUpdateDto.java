package by.softclub.test.clientservice.dto.client;

import by.softclub.test.clientservice.dto.address.AddressDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataDto;
import by.softclub.test.clientservice.entity.ClientStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Size(max = 50, message = "Middle name must not exceed 50 characters")
    private String middleName;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private ClientStatus status;

    @Valid
    private PassportDataDto passportData;

    @Valid
    private ContactInfoDto contactInfo;

    @Valid
    private AddressDto address;
}
