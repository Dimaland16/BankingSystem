package by.softclub.test.deposit_service.mapper;


import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionRequestDto;
import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionResponseDto;
import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionUpdateDto;
import by.softclub.test.deposit_service.entity.DepositCondition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepositConditionMapper {

    DepositCondition toEntity(DepositConditionRequestDto requestDto);

    DepositConditionResponseDto toDto(DepositCondition condition);

    void updateEntity(DepositConditionUpdateDto updateDto, @MappingTarget DepositCondition condition);
}