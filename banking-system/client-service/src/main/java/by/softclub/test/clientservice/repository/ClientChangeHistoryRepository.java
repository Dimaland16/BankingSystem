package by.softclub.test.clientservice.repository;

import by.softclub.test.clientservice.entity.ClientChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientChangeHistoryRepository extends JpaRepository<ClientChangeHistory, Long> {
}
