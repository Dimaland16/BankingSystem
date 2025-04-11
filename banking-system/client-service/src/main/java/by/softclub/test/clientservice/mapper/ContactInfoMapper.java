package by.softclub.test.clientservice.mapper;

import by.softclub.test.clientservice.dto.contactInfo.ContactInfoDto;
import by.softclub.test.clientservice.entity.ContactInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactInfoMapper {

    ContactInfo toEntity(ContactInfoDto contactInfoDto);

    ContactInfoDto toDto(ContactInfo contactInfo);
}
