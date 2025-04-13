package by.softclub.test.clientservice.dto.client;

import by.softclub.test.clientservice.dto.address.AddressRequestDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoRequestDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataRequestDto;
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
public class ClientRequestDto {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Middle name is required")
    @Size(max = 50, message = "Middle name must not exceed 50 characters")
    private String middleName;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotNull(message = "Passport data is required")
    @Valid
    private PassportDataRequestDto passportData;

    @NotNull(message = "Contact info is required")
    @Valid
    private ContactInfoRequestDto contactInfo;

    @NotNull(message = "Address info is required")
    @Valid
    private AddressRequestDto address;
}
