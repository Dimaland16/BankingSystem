package by.softclub.test.loanservice.mapper;

import by.softclub.test.loanservice.dto.payment.PaymentHistoryDto;
import by.softclub.test.loanservice.entity.PaymentHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {

    PaymentHistory toEntity(PaymentHistoryDto dto);

    PaymentHistoryDto toDto(PaymentHistory entity);
}