package by.softclub.test.clientservice.mapper;

import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PassportDataMapper.class, ContactInfoMapper.class, AddressMapper.class})
public interface ClientMapper {

    Client toEntity(ClientRequestDto requestDto);

    ClientResponseDto toDto(Client client);
}
