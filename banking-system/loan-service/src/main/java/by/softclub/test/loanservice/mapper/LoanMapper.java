package by.softclub.test.loanservice.mapper;

import by.softclub.test.loanservice.dto.loan.LoanRequestDto;
import by.softclub.test.loanservice.dto.loan.LoanResponseDto;
import by.softclub.test.loanservice.dto.loan.LoanTermsDto;
import by.softclub.test.loanservice.dto.loan.LoanUpdateDto;
import by.softclub.test.loanservice.entity.Loan;
import by.softclub.test.loanservice.entity.LoanTerms;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LoanTermsMapper.class, PaymentScheduleMapper.class, PaymentHistoryMapper.class})
public interface LoanMapper {

    Loan toEntity(LoanRequestDto requestDto);

    LoanResponseDto toDto(Loan loan);

    //LoanTerms loanTermsDtoToEntity(LoanTermsDto loanTermsDto);
}