package by.softclub.test.clientservice.repository;

import by.softclub.test.clientservice.entity.PassportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportDataRepository extends JpaRepository<PassportData, Long> {

    boolean existsBySeriesAndNumber(String series, String number);

    boolean existsBySeriesAndNumberAndClientIdNot(String newSeries, String newNumber, Long clientId);
}
