package by.softclub.test.clientservice.mapper;

import by.softclub.test.clientservice.dto.address.AddressRequestDto;
import by.softclub.test.clientservice.dto.clientChangeHistory.ClientChangeHistoryResponseDto;
import by.softclub.test.clientservice.entity.Address;
import by.softclub.test.clientservice.entity.ClientChangeHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientChangeHistoryMapper {

    ClientChangeHistory toEntity(ClientChangeHistoryResponseDto clientChangeHistoryResponseDto);

    @Mapping(target = "clientId", source = "client.id")
    ClientChangeHistoryResponseDto toDto(ClientChangeHistory clientChangeHistory);
}
