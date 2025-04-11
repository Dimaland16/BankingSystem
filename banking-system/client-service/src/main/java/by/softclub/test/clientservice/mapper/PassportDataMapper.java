package by.softclub.test.clientservice.mapper;

import by.softclub.test.clientservice.dto.passportData.PassportDataDto;
import by.softclub.test.clientservice.entity.PassportData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportDataMapper {

    PassportData toEntity(PassportDataDto passportDataDto);

    PassportDataDto toDto(PassportData passportData);
}
