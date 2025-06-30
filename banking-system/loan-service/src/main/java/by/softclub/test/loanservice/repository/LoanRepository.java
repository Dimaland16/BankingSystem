package by.softclub.test.loanservice.repository;

import by.softclub.test.loanservice.entity.Loan;
import by.softclub.test.loanservice.entity.LoanProductType;
import by.softclub.test.loanservice.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{
    List<Loan> findByContractNumber(String contractNumber);
    List<Loan> findByProductType(LoanProductType productType);
    List<Loan> findByStatus(LoanStatus status);
    List<Loan> findByLoanAmountGreaterThan(BigDecimal amount); //AmountGreaterThan(BigDecimal amount);
}

/*
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE l.contractNumber = :contractNumber")
    List<Loan> findByContractNumber(@Param("contractNumber") String contractNumber);

    @Query("SELECT l FROM Loan l WHERE l.productType = :productType")
    List<Loan> findByProductType(@Param("productType") LoanProductType productType);

    @Query(value = "SELECT * FROM loans l WHERE l.status = ?1", nativeQuery = true)
    List<Loan> findByStatus(String status);

    @Query("SELECT l FROM Loan l WHERE l.loanAmount > :amount")
    List<Loan> findLoansWithAmountGreaterThan(@Param("amount") BigDecimal amount);

}
*/