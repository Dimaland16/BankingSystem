package by.softclub.test.clientservice.repository;

import by.softclub.test.clientservice.entity.PassportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportDataRepository extends JpaRepository<PassportData, Long> {
}
