package by.softclub.test.deposit_service.mapper;

import by.softclub.test.deposit_service.dto.depositType.DepositTypeRequestDto;
import by.softclub.test.deposit_service.dto.depositType.DepositTypeResponseDto;
import by.softclub.test.deposit_service.entity.DepositType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepositTypeMapper {

    DepositTypeMapper INSTANCE = Mappers.getMapper(DepositTypeMapper.class);

    DepositTypeResponseDto toDto(DepositType depositType);

    DepositType toEntity(DepositTypeRequestDto depositType);
}
