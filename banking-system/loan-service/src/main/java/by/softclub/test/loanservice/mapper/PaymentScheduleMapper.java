package by.softclub.test.loanservice.mapper;

import by.softclub.test.loanservice.dto.payment.PaymentScheduleDto;
import by.softclub.test.loanservice.entity.PaymentSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentScheduleMapper {

    PaymentSchedule toEntity(PaymentScheduleDto dto);

    PaymentScheduleDto toDto(PaymentSchedule entity);
}