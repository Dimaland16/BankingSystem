package by.softclub.test.deposit_service.dto.Operation;

import by.softclub.test.deposit_service.entity.Deposit;
import by.softclub.test.deposit_service.entity.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OperationResponseDto {

    private Long id;

    private Long depositId;

    private OperationType operationType;

    private BigDecimal amount;

    private LocalDateTime operationDate;

}
