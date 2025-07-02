package by.softclub.test.deposit_service.dto.Operation;

import by.softclub.test.deposit_service.entity.OperationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OperationRequestDto {

    private Long depositId;

    @NotNull
    private OperationType operationType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime operationDate;

}
