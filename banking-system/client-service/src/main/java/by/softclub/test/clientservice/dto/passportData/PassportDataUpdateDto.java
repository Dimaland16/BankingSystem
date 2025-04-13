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
public class PassportDataUpdateDto {

    @Size(max = 10, message = "Passport series must not exceed 10 characters")
    private String series;

    @Size(max = 20, message = "Passport number must not exceed 20 characters")
    private String number;

    private LocalDate issueDate;

    @Size(max = 200, message = "Passport issuer must not exceed 200 characters")
    private String issuer;

    @Size(max = 20, message = "Passport code must not exceed 20 characters")
    private String code;
}
