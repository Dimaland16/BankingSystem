package by.softclub.test.deposit_service.repository;

import by.softclub.test.deposit_service.entity.DepositCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositConditionRepository extends JpaRepository<DepositCondition, Long> {
}
