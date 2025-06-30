package by.softclub.test.deposit_service.repository;

import by.softclub.test.deposit_service.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    /**
     * Находит все операции, связанные с указанным депозитом.
     *
     * @param depositId идентификатор депозита
     * @return список операций для указанного депозита
     */
    List<Operation> findByDepositId(Long depositId);

    List<Operation> findByDepositIdAndOperationDateBetween(Long depositId, LocalDateTime startDate, LocalDateTime endDate);

    List<Operation> findByDepositIdAndOperationDateBefore(Long depositId, LocalDateTime date);
}