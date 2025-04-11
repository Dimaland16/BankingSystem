package by.softclub.test.clientservice.mapper;

import by.softclub.test.clientservice.dto.address.AddressDto;
import by.softclub.test.clientservice.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressDto addressDto);

    AddressDto toDto(Address address);
}
