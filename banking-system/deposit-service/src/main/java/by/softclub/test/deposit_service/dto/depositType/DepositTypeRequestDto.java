package by.softclub.test.deposit_service.dto.depositType;

import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionRequestDto;
import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionResponseDto;
import lombok.Data;

@Data
public class DepositTypeRequestDto {
    private String name;
    private DepositConditionRequestDto condition;
}
