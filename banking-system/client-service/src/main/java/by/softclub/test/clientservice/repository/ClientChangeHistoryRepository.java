package by.softclub.test.clientservice.repository;

import by.softclub.test.clientservice.entity.ClientChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientChangeHistoryRepository extends JpaRepository<ClientChangeHistory, Long> {
    List<ClientChangeHistory> findByClientIdOrderByChangeDateDesc(Long clientId);

    void deleteByClientId(Long id);
}