package by.softclub.test.deposit_service.repository;

import by.softclub.test.deposit_service.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    /**
     * Находит депозит по уникальному номеру договора.
     *
     * @param contractNumber номер договора
     * @return Optional с депозитом, если найден, иначе пустой Optional
     */
    Optional<Deposit> findByContractNumber(String contractNumber);

    boolean existsByContractNumber(String contractNumber);
}