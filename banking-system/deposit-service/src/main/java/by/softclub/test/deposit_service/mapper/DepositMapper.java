package by.softclub.test.deposit_service.mapper;

import by.softclub.test.deposit_service.dto.deposit.DepositRequestDto;
import by.softclub.test.deposit_service.dto.deposit.DepositResponseDto;
import by.softclub.test.deposit_service.dto.deposit.DepositUpdateDto;
import by.softclub.test.deposit_service.dto.depositType.DepositTypeResponseDto;
import by.softclub.test.deposit_service.entity.Deposit;
import by.softclub.test.deposit_service.entity.DepositType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {DepositTypeMapper.class, OperationMapper.class, DepositConditionMapper.class})
public interface DepositMapper {

    Deposit toEntity(DepositRequestDto requestDto);

    @Mapping(target = "depositType", source = "depositType", qualifiedByName = "mapDepositTypeToDto")
    @Mapping(target = "conditions", source = "depositType.condition") // Берем условия из depositType
    DepositResponseDto toDto(Deposit deposit);

    @Named("mapDepositTypeToDto")
    default DepositTypeResponseDto mapDepositTypeToDto(DepositType depositType) {
        if (depositType == null) return null;
        return DepositTypeMapper.INSTANCE.toDto(depositType);
    }

}