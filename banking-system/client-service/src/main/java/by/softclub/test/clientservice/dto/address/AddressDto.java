package by.softclub.test.clientservice.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String postalCode;

    private String region;

    private String city;

    private String street;

    private String house;

    private String apartment;
}
