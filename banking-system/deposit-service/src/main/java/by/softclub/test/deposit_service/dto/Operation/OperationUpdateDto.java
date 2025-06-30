package by.softclub.test.deposit_service.dto.Operation;

import by.softclub.test.deposit_service.entity.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OperationUpdateDto {

    private OperationType operationType;

    private BigDecimal amount;

    private LocalDateTime operationDate;

}
