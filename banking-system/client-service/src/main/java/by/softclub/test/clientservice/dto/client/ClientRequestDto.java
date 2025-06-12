package by.softclub.test.clientservice.dto.client;

import by.softclub.test.clientservice.dto.address.AddressRequestDto;
import by.softclub.test.clientservice.dto.contactInfo.ContactInfoRequestDto;
import by.softclub.test.clientservice.dto.passportData.PassportDataRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s\\-']+$", message = "Invalid first name format")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s\\-']+$", message = "Invalid last name format")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Middle name is required")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s\\-']+$", message = "Invalid middle name format")
    @Size(max = 50, message = "Middle name must not exceed 50 characters")
    private String middleName;

    @NotBlank(message = "Password is required")
    @Size(max = 50, message = "Password must not exceed 50 characters")
    private String password;

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
