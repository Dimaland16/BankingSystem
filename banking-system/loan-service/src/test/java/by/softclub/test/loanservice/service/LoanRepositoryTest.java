package by.softclub.test.loanservice.service;

import by.softclub.test.loanservice.entity.Loan;
import by.softclub.test.loanservice.entity.LoanProductType;
import by.softclub.test.loanservice.entity.LoanStatus;
import by.softclub.test.loanservice.entity.LoanTerms;
import by.softclub.test.loanservice.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Использует основную БД (или H2, если указана)
class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void testSaveLoan() {
        // Given
        Loan loan = new Loan();
        //loan.setLoanId(1L);
        loan.setClientId(100L);
        loan.setContractNumber("LOAN-001");
        loan.setProductType(LoanProductType.CONSUMER_LOAN);
        loan.setLoanAmount(new BigDecimal("10000"));
        loan.setInterestRate(new BigDecimal("5.00"));
        loan.setLoanTermMonths(12);
        loan.setContractDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusYears(1));
        //loan.setStatus(LoanStatus.CREATED);

        loan.setStatus(LoanStatus.ACTIVE);
        loan.setLoanTerms(new LoanTerms(true, "No penalty", 0, 0));

        // When
        Loan savedLoan = loanRepository.save(loan);

        // Then
        assertThat(savedLoan).isNotNull();
        assertThat(savedLoan.getLoanId()).isNotNull();
        assertThat(savedLoan.getContractNumber()).isEqualTo("LOAN-001");
    }

    @Test
    void testGetLoanById() {
        // Given
        Loan loan = new Loan();
        //loan.setLoanId(2L);
        loan.setClientId(100L);
        loan.setContractNumber("LOAN-002");
        loan.setProductType(LoanProductType.MORTGAGE);
        loan.setLoanAmount(new BigDecimal("20000"));
        loan.setInterestRate(new BigDecimal("4.50"));
        loan.setLoanTermMonths(24);
        loan.setContractDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusYears(2));
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setLoanTerms(new LoanTerms(false, "Fixed penalty", 5, 30));

        Loan saved = loanRepository.save(loan);

        // When
        Optional<Loan> fetched = loanRepository.findById(saved.getLoanId());

        // Then
        assertThat(fetched).isPresent();
        assertThat(fetched.get().getContractNumber()).isEqualTo("LOAN-002");
        assertThat(fetched.get().getProductType()).isEqualTo(LoanProductType.MORTGAGE);
    }

    @Test
    void testFindAllLoans() {
        // Given
        Loan loan1 = new Loan();
        loan1.setClientId(100L);
        loan1.setContractNumber("LOAN-003");
        loan1.setProductType(LoanProductType.CONSUMER_LOAN);
        loan1.setLoanAmount(new BigDecimal("15000"));
        loan1.setStatus(LoanStatus.ACTIVE);

        Loan loan2 = new Loan();
        loan2.setClientId(200L);
        loan2.setContractNumber("LOAN-004");
        loan2.setProductType(LoanProductType.CONSUMER_LOAN);
        loan2.setLoanAmount(new BigDecimal("5000"));
        loan2.setStatus(LoanStatus.ACTIVE);

        loanRepository.save(loan1);
        loanRepository.save(loan2);

        // When
        List<Loan> loans = loanRepository.findAll();

        // Then
        assertThat(loans).hasSize(2);
        assertThat(loans).extracting(Loan::getContractNumber)
                .contains("LOAN-003", "LOAN-004");
    }

    @Test
    void testUpdateLoan() {
        // Given
        Loan loan = new Loan();
        loan.setClientId(100L);
        loan.setContractNumber("LOAN-005");
        loan.setProductType(LoanProductType.MORTGAGE);
        loan.setLoanAmount(new BigDecimal("25000"));
        loan.setStatus(LoanStatus.ACTIVE);

        Loan saved = loanRepository.save(loan);

        // When
        saved.setContractNumber("UPDATED-LOAN-005");
        saved.setProductType(LoanProductType.CONSUMER_LOAN);
        loanRepository.save(saved);

        Optional<Loan> updated = loanRepository.findById(saved.getLoanId());

        // Then
        assertThat(updated).isPresent();
        assertThat(updated.get().getContractNumber()).isEqualTo("UPDATED-LOAN-005");
        assertThat(updated.get().getProductType()).isEqualTo(LoanProductType.CONSUMER_LOAN);
    }

    @Test
    void testDeleteLoan() {
        // Given
        Loan loan = new Loan();
        loan.setClientId(100L);
        loan.setContractNumber("LOAN-006");
        loan.setStatus(LoanStatus.ACTIVE);

        Loan saved = loanRepository.save(loan);

        // When
        loanRepository.deleteById(saved.getLoanId());

        // Then
        Optional<Loan> deleted = loanRepository.findById(saved.getLoanId());
        assertThat(deleted).isNotPresent();
    }
/*

    @Test
    void testSaveLoanWithNullContractNumber_shouldFail() {
        Loan loan = new Loan();
        // contractNumber == null
        assertThatThrownBy(() -> loanRepository.saveAndFlush(loan))
                .isInstanceOf(DataIntegrityViolationException.class);


}
*/

    @Test
    void testSaveTwoLoansWithSameContractNumber_shouldFail() {
        Loan loan1 = new Loan();
        loan1.setClientId(100L);
        loan1.setContractNumber("LOAN-DUPLICATE");
        loan1.setStatus(LoanStatus.ACTIVE);

        Loan loan2 = new Loan();
        loan2.setClientId(200L);
        loan2.setContractNumber("LOAN-DUPLICATE");
        loan2.setStatus(LoanStatus.ACTIVE);


        loanRepository.save(loan1);
        assertThatThrownBy(() -> loanRepository.saveAndFlush(loan2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}