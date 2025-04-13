package by.softclub.test.loanservice.mapper;

import by.softclub.test.loanservice.dto.loan.LoanTermsDto;
import by.softclub.test.loanservice.entity.LoanTerms;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanTermsMapper {

    LoanTerms toEntity(LoanTermsDto dto);

    LoanTermsDto toDto(LoanTerms entity);
}