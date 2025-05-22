package by.softclub.test.loanservice.service;

import by.softclub.test.loanservice.dto.loan.LoanRequestDto;
import by.softclub.test.loanservice.dto.loan.LoanResponseDto;
import by.softclub.test.loanservice.dto.loan.LoanTermsDto;
import by.softclub.test.loanservice.dto.loan.LoanUpdateDto;
import by.softclub.test.loanservice.entity.*;
import by.softclub.test.loanservice.mapper.LoanMapper;
import by.softclub.test.loanservice.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    private Loan loan;
    private LoanRequestDto requestDto;
    private LoanResponseDto responseDto;
    private LoanUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        loan = new Loan();
        loan.setLoanId(1L);
        loan.setClientId(100L);
        loan.setContractNumber("LOAN-001");
        loan.setProductType(LoanProductType.CONSUMER_LOAN);
        loan.setLoanAmount(new BigDecimal("10000"));
        loan.setInterestRate(new BigDecimal("10.00"));
        loan.setLoanTermMonths(24);
        loan.setContractDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusYears(2));
        //loan.setStatus(LoanStatus.PENDING);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setCurrentDebt(new BigDecimal("9000"));
        loan.setLoanTerms(new LoanTerms(true, "5% penalty", 30, 0));

        requestDto = new LoanRequestDto();
        requestDto.setClientId(100L);
        requestDto.setContractNumber("LOAN-001");
        requestDto.setProductType(LoanProductType.CONSUMER_LOAN);
        requestDto.setLoanAmount(new BigDecimal("10000"));
        requestDto.setInterestRate(new BigDecimal("10.00"));
        requestDto.setLoanTermMonths(24);
        requestDto.setContractDate(LocalDate.now());
        requestDto.setLoanTerms(new LoanTermsDto(true, "5% penalty", 30, 0));

        responseDto = new LoanResponseDto();
        responseDto.setLoanId(1L);
        responseDto.setClientId(100L);
        responseDto.setContractNumber("LOAN-001");
        responseDto.setProductType(LoanProductType.CONSUMER_LOAN);
        responseDto.setLoanAmount(new BigDecimal("10000"));
        responseDto.setInterestRate(new BigDecimal("10.00"));
        responseDto.setLoanTermMonths(24);
        responseDto.setContractDate(LocalDate.now());
        responseDto.setEndDate(LocalDate.now().plusYears(2));
        responseDto.setStatus(LoanStatus.ACTIVE);
        responseDto.setCurrentDebt(new BigDecimal("9000"));
        responseDto.setLoanTerms(new LoanTermsDto(true, "5% penalty", 30, 0));

        updateDto = new LoanUpdateDto();
        updateDto.setContractNumber("UPDATED-LOAN-001");
        updateDto.setProductType(LoanProductType.MORTGAGE);
        updateDto.setLoanAmount(new BigDecimal("15000"));
        updateDto.setInterestRate(new BigDecimal("8.50"));
        updateDto.setLoanTermMonths(36);
        updateDto.setEndDate(LocalDate.now().plusYears(3));
        updateDto.setCurrentDebt(new BigDecimal("7000"));
    }

    @Test
    void testCreateLoan_ShouldReturnSavedLoanDto() {
        when(loanMapper.toEntity(requestDto)).thenReturn(loan);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDto(loan)).thenReturn(responseDto);

        LoanResponseDto result = loanService.createLoan(requestDto);

        assertThat(result).isNotNull();
        assertThat(result.getContractNumber()).isEqualTo("LOAN-001");
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void testGetLoanById_ShouldReturnLoanDto() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanMapper.toDto(loan)).thenReturn(responseDto);

        LoanResponseDto result = loanService.getLoanById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getLoanId()).isEqualTo(1L);
    }

    @Test
    void testGetLoanById_ThrowsException_WhenLoanNotFound() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.getLoanById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void testGetAllLoans_ShouldReturnListOfDtos() {
        List<Loan> loans = Collections.singletonList(loan);
        when(loanRepository.findAll()).thenReturn(loans);
        when(loanMapper.toDto(any(Loan.class))).thenReturn(responseDto);

        List<LoanResponseDto> result = loanService.getAllLoans();

        assertThat(result).hasSize(1);
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void testUpdateLoan_ShouldUpdateFields() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDto(loan)).thenReturn(responseDto);

        LoanResponseDto result = loanService.updateLoan(1L, updateDto);

        assertThat(result.getContractNumber()).isEqualTo("LOAN-001");
        assertThat(result.getProductType()).isEqualTo("UPDATED-LOAN-001");
        assertThat(result.getLoanAmount()).isEqualTo(new BigDecimal("15000"));
    }

    @Test
    void testUpdateLoan_ThrowsException_WhenLoanNotFound() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.updateLoan(1L, updateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Loan not found");
    }

    @Test
    void testDeleteLoan_ShouldCallRepositoryDelete() {
        doNothing().when(loanRepository).deleteById(1L);

        loanService.deleteLoan(1L);

        verify(loanRepository, times(1)).deleteById(1L);
    }
}