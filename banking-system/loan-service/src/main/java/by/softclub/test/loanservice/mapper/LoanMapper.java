package by.softclub.test.loanservice.mapper;

import by.softclub.test.loanservice.dto.loan.LoanRequestDto;
import by.softclub.test.loanservice.dto.loan.LoanResponseDto;
import by.softclub.test.loanservice.dto.loan.LoanTermsDto;
import by.softclub.test.loanservice.dto.loan.LoanUpdateDto;
import by.softclub.test.loanservice.entity.Loan;
import by.softclub.test.loanservice.entity.LoanTerms;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")  //, uses = {LoanTermsMapper.class, PaymentScheduleMapper.class, PaymentHistoryMapper.class})
public interface LoanMapper {

    /*@Mapping(source = "loanId", target = "loanId")
    @Mapping(source = "clientId", target = "clientId")
    @Mapping(source = "contractNumber", target = "contractNumber")
    @Mapping(source = "productType", target = "productType")
    @Mapping(source = "loanAmount", target = "loanAmount")
    @Mapping(source = "interestRate", target = "interestRate")
    @Mapping(source = "loanTermMonths", target = "loanTermMonths")
    @Mapping(source = "contractDate", target = "contractDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "status", target = "status", defaultExpression = "java(LoanStatus.NEW)")
    @Mapping(source = "paymentSchedule", target = "paymentSchedule")
    @Mapping(source = "paymentHistory", target = "paymentHistory")
    @Mapping(source = "currentDebt", target = "currentDebt", defaultExpression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(source = "loanTerms", target = "loanTerms")*/
    Loan toEntity(LoanRequestDto dto);


/*
    @Mapping(source = "loanId", target = "loanId")
    @Mapping(source = "clientId", target = "clientId")
    @Mapping(source = "contractNumber", target = "contractNumber")
    @Mapping(source = "productType", target = "productType")
    @Mapping(source = "loanAmount", target = "loanAmount")
    @Mapping(source = "interestRate", target = "interestRate")
    @Mapping(source = "loanTermMonths", target = "loanTermMonths")
    @Mapping(source = "contractDate", target = "contractDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "paymentSchedule", target = "paymentSchedule")
    @Mapping(source = "paymentHistory", target = "paymentHistory")
    @Mapping(source = "currentDebt", target = "currentDebt")
    @Mapping(source = "loanTerms", target = "loanTerms")*/
    LoanResponseDto toDto(Loan loan);
}