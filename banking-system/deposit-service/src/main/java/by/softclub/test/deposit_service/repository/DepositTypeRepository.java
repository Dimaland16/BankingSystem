package by.softclub.test.deposit_service.repository;

import by.softclub.test.deposit_service.entity.DepositCondition;
import by.softclub.test.deposit_service.entity.DepositType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositTypeRepository extends JpaRepository<DepositType, Long>{
    Optional<DepositType> findByName(@NotNull String depositTypeName);

    boolean existsByName(String name);
}

