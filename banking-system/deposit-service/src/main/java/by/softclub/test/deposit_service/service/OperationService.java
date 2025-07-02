package by.softclub.test.deposit_service.service;

import by.softclub.test.deposit_service.dto.Operation.OperationResponseDto;
import by.softclub.test.deposit_service.entity.Operation;
import by.softclub.test.deposit_service.mapper.OperationMapper;
import by.softclub.test.deposit_service.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    @Autowired
    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    public List<Operation> getOperationsByPeriod(Long depositId, LocalDateTime startDate, LocalDateTime endDate) {
        return operationRepository.findByDepositIdAndOperationDateBetween(depositId, startDate, endDate);
    }

    public List<Operation> getAllOperationsBefore(Long depositId, LocalDateTime date) {
        return operationRepository.findByDepositIdAndOperationDateBefore(depositId, date);
    }

    public List<OperationResponseDto> mapToDtos(List<Operation> operations) {
        return operations.stream()
                .map(operationMapper::toDto)
                .toList();
    }
}
