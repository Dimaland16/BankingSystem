package by.softclub.test.clientservice.dto.passportData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassportDataDto {

    private String series;

    private String number;

    private LocalDate issueDate;

    private String issuer;

    private String code;
}
