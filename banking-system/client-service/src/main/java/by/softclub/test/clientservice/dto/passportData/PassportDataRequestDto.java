package by.softclub.test.clientservice.dto.passportData;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PassportDataRequestDto {

    @NotBlank(message = "Passport series is required")
    @Size(max = 10, message = "Passport series must not exceed 10 characters")
    private String series;

    @NotBlank(message = "Passport number is required")
    @Size(max = 20, message = "Passport number must not exceed 20 characters")
    private String number;

    @NotNull(message = "Passport issue date is required")
    @Past(message = "Issue date must be in the past")
    private LocalDate issueDate;

    @NotBlank(message = "Passport issuer is required")
    @Size(max = 200, message = "Passport issuer must not exceed 200 characters")
    private String issuer;

    @NotBlank(message = "Passport code is required")
    @Size(max = 20, message = "Passport code must not exceed 20 characters")
    private String code;
}
