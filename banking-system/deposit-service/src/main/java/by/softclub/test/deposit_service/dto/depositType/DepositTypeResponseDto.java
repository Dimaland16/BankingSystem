package by.softclub.test.deposit_service.dto.depositType;

import by.softclub.test.deposit_service.dto.depositCondition.DepositConditionResponseDto;
import lombok.Data;

@Data
public class DepositTypeResponseDto {
    private Long id;
    private String name;
    private DepositConditionResponseDto condition;
}
