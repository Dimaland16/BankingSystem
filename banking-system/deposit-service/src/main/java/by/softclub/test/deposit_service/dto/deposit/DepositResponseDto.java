package by.softclub.test.deposit_service.dto.deposit;

import by.softclub.test.deposit_service.dto.Operation.OperationResponseDto;
import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionResponseDto;
import by.softclub.test.deposit_service.dto.depositType.DepositTypeResponseDto;
import by.softclub.test.deposit_service.entity.DepositStatus;
import by.softclub.test.deposit_service.entity.DepositType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class DepositResponseDto {

    private Long id;

    private Long clientId;

    private String contractNumber;

    private DepositTypeResponseDto depositType;

    private BigDecimal contractAmount;

    private BigDecimal interestRate;

    private Integer contractTerm;

    private LocalDate conclusionDate;

    private LocalDate endDate;

    private DepositStatus status;

    private List<OperationResponseDto> operations;

    private DepositConditionResponseDto conditions;

}
