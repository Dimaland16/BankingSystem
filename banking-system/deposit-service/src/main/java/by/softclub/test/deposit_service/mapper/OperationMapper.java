package by.softclub.test.deposit_service.mapper;

import by.softclub.test.deposit_service.dto.Operation.OperationResponseDto;
import by.softclub.test.deposit_service.dto.deposit.DepositRequestDto;
import by.softclub.test.deposit_service.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(target = "depositId", source = "deposit.id")
    OperationResponseDto toDto(Operation operation);

    Operation toEntity(DepositRequestDto operationDTO);

}
