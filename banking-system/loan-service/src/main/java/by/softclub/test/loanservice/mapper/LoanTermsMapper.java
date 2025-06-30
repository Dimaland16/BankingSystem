package by.softclub.test.loanservice.mapper;

import by.softclub.test.loanservice.dto.loan.LoanTermsDto;
import by.softclub.test.loanservice.entity.LoanTerms;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoanTermsMapper {

    //LoanTermsMapper INSTANCE = Mappers.getMapper(LoanTermsMapper.class);

    LoanTerms toEntity(LoanTermsDto dto);

    LoanTermsDto toDto(LoanTerms entity);
}