package by.softclub.test.loanservice.mapper;

import by.softclub.test.loanservice.dto.loan.LoanRequestDto;
import by.softclub.test.loanservice.dto.loan.LoanResponseDto;
import by.softclub.test.loanservice.dto.loan.LoanTermsDto;
import by.softclub.test.loanservice.dto.loan.LoanUpdateDto;
import by.softclub.test.loanservice.entity.Loan;
import by.softclub.test.loanservice.entity.LoanTerms;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LoanTermsMapper.class, PaymentScheduleMapper.class, PaymentHistoryMapper.class})
public interface LoanMapper {
    /*
        @Mapping(target = "loanId", source = "loanId")
        @Mapping(target = "clientId", source = "clientId")
        @Mapping(target = "contractNumber", source = "contractNumber")
        @Mapping(target = "productType", source = "productType")
        @Mapping(target = "loanAmount", source = "loanAmount")
        @Mapping(target = "interestRate", source = "interestRate")
        @Mapping(target = "loanTermMonths", source = "loanTermMonths")
        @Mapping(target = "contractDate", source = "contractDate")
        @Mapping(target = "endDate", source = "endDate")

        @Mapping(target = "status", source = "status")
        @Mapping(target = "paymentSchedule", source = "paymentSchedule")
        @Mapping(target = "paymentHistory", source = "paymentHistory")
        @Mapping(target = "currentDebt", source = "currentDebt")

    @Mapping(target = "loanTerms", source = "loanTerms")


    @Mapping(target = "loanId", source = "loanId")
    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "contractNumber", source = "contractNumber")
    @Mapping(target = "productType", source = "productType")
    @Mapping(target = "loanAmount", source = "loanAmount")
    @Mapping(target = "interestRate", source = "interestRate")
    @Mapping(target = "loanTermMonths", source = "loanTermMonths")
    @Mapping(target = "contractDate", source = "contractDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "paymentSchedule", source = "paymentSchedule")
    @Mapping(target = "paymentHistory", source = "paymentHistory")
    @Mapping(target = "currentDebt", source = "currentDebt")
    @Mapping(target = "loanTerms", source = "loanTerms")

     */

    Loan toEntity(LoanRequestDto requestDto);

    LoanResponseDto toDto(Loan loan);
}