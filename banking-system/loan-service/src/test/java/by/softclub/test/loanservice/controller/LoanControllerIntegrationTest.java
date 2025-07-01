package by.softclub.test.loanservice.controller;

import by.softclub.test.loanservice.dto.LoanClientDto;
import by.softclub.test.loanservice.dto.LoanClientStatus;
import by.softclub.test.loanservice.dto.loan.*;
import by.softclub.test.loanservice.entity.Loan;
import by.softclub.test.loanservice.entity.LoanProductType;
import by.softclub.test.loanservice.entity.LoanStatus;
import by.softclub.test.loanservice.mapper.LoanMapper;
import by.softclub.test.loanservice.repository.LoanRepository;
import by.softclub.test.loanservice.service.ClientService;
import by.softclub.test.loanservice.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
                "spring.profiles.active=integration"
        }
)
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanRepository loanRepository;

    private Loan testLoan; // ← добавь поле

    @Autowired
    private ObjectMapper objectMapper;

    //мокирую ClienServoce
    @MockBean
    //@Autowired
    private ClientService clientService;

    private LoanRequestDto validLoanRequest;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAllInBatch();
        Loan loan = new Loan();
        //loan.setLoanId(1L); //id генерит бд автоматически( @GeneratedValue(strategy = GenerationType.IDENTITY) )
        loan.setClientId(100L);
        loan.setContractNumber("LOAN-001");
        loan.setProductType(LoanProductType.CONSUMER_LOAN);
        loan.setLoanAmount(new BigDecimal("10000"));
        loan.setInterestRate(new BigDecimal("5.00"));
        loan.setLoanTermMonths(12);
        loan.setContractDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusYears(1));
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setCurrentDebt(new BigDecimal("9000"));

        testLoan = loanRepository.saveAndFlush(loan);

        System.out.println("Saved loan ID = " + testLoan.getLoanId());
    }


/*
    @Test
    void testCreateLoan_shouldReturn201_withoutMock() throws Exception {
        LoanRequestDto dto = new LoanRequestDto();
        //dto.setClientId(1L);
        dto.setLoanId(testLoan.getLoanId());
        dto.setClientId(testLoan.getLoanId());
        dto.setContractNumber("LOAN-001");
        dto.setProductType(LoanProductType.CONSUMER_LOAN);
        dto.setLoanAmount(new BigDecimal("10000"));
        dto.setInterestRate(new BigDecimal("5.00"));
        dto.setLoanTermMonths(12);
        dto.setContractDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusYears(1));
        dto.setLoanTerms(new LoanTermsDto(true, "No penalty", 30, 12));
        dto.setStatus(LoanStatus.ACTIVE);
        dto.setCurrentDebt(new BigDecimal("9000"));

        //LoanClientDto mockClient = new LoanClientDto();
        LoanClientDto mockClient = clientService.getClientByPassport("MP", "83456456");
        mockClient.setId(100L);
        mockClient.setBirthDate(LocalDate.now().minusYears(26));
        mockClient.setStatus(LoanClientStatus.ACTIVE);
*/

/*
        // mock getClientForLoan("MP", "83456456")
        LoanClientDto mockClient = new LoanClientDto();
        mockClient.setId(100L);
        mockClient.setBirthDate(LocalDate.now().minusYears(26));
        mockClient.setStatus(LoanClientStatus.ACTIVE);
        when(clientService.getClientByPassport("MP", "83456456")).thenReturn(mockClient);
*/

/*        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contractNumber").value("LOAN-001"))
                .andExpect(jsonPath("$.loanAmount").value("10000"));
    }*/


    @Test
    void testCreateLoan_shouldReturn201() throws Exception {
        LoanRequestDto dto = new LoanRequestDto();
        //dto.setClientId(1L);
        dto.setLoanId(testLoan.getLoanId());
        dto.setClientId(testLoan.getLoanId());
        dto.setContractNumber("LOAN-001");
        dto.setProductType(LoanProductType.CONSUMER_LOAN);
        dto.setLoanAmount(new BigDecimal("10000"));
        dto.setInterestRate(new BigDecimal("5.00"));
        dto.setLoanTermMonths(12);
        dto.setContractDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusYears(1));
        dto.setLoanTerms(new LoanTermsDto(true, "No penalty", 30, 12));
        dto.setStatus(LoanStatus.ACTIVE);
        dto.setCurrentDebt(new BigDecimal("9000"));

        /*
        //LoanClientDto mockClient = new LoanClientDto();
        LoanClientDto mockClient = clientService.getClientByPassport("MP", "83456456");
        mockClient.setId(100L);
        mockClient.setBirthDate(LocalDate.now().minusYears(26));
        mockClient.setStatus(LoanClientStatus.ACTIVE);
        */

        // Мокаем getClientForLoan("MP", "83456456")
        LoanClientDto mockClient = new LoanClientDto();
        mockClient.setId(100L);
        mockClient.setBirthDate(LocalDate.now().minusYears(26));
        mockClient.setStatus(LoanClientStatus.ACTIVE);

        when(clientService.getClientByPassport("MP", "83456456")).thenReturn(mockClient);

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contractNumber").value("LOAN-001"))
                .andExpect(jsonPath("$.loanAmount").value("10000"));
    }

    @Test
    void testCreateLoan_shouldReturn201_whenValidRequest() throws Exception {

        // Подготавливаем DTO с захардкоженными паспортными данными
        validLoanRequest = new LoanRequestDto();
        validLoanRequest.setLoanId(testLoan.getLoanId());
        validLoanRequest.setClientId(100L);
        validLoanRequest.setContractNumber("LOAN-001");
        validLoanRequest.setProductType(LoanProductType.CONSUMER_LOAN);
        validLoanRequest.setLoanAmount(new BigDecimal("10000"));
        validLoanRequest.setInterestRate(new BigDecimal("5.00"));
        validLoanRequest.setLoanTermMonths(12);
        validLoanRequest.setContractDate(LocalDate.now());
        validLoanRequest.setEndDate(LocalDate.now().plusYears(1));
        validLoanRequest.setLoanTerms(new LoanTermsDto(true, "No penalty", 30, 12));
        validLoanRequest.setStatus(LoanStatus.ACTIVE);
        validLoanRequest.setCurrentDebt(new BigDecimal("9000"));

        // mock getClientForLoan("MP", "83456456")
        LoanClientDto mockClient = new LoanClientDto();
        mockClient.setId(100L);
        mockClient.setBirthDate(LocalDate.now().minusYears(26));
        mockClient.setStatus(LoanClientStatus.ACTIVE);

        when(clientService.getClientByPassport("MP", "83456456")).thenReturn(mockClient);

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoanRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contractNumber").value("LOAN-001"));
    }

    @Test
    void testCreateLoan_shouldSavePaymentScheduleWithValidStatus() throws Exception {
        LoanRequestDto dto = new LoanRequestDto();
        dto.setLoanId(testLoan.getLoanId());
        dto.setClientId(100L);
        dto.setContractNumber("LOAN-NEW");
        dto.setProductType(LoanProductType.CONSUMER_LOAN);
        dto.setLoanAmount(new BigDecimal("10000"));
        dto.setInterestRate(new BigDecimal("5.00"));
        dto.setLoanTermMonths(12);
        dto.setContractDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusYears(1));
        dto.setLoanTerms(new LoanTermsDto(true, "No penalty", 30, 12));
        dto.setStatus(LoanStatus.ACTIVE);
        dto.setCurrentDebt(new BigDecimal("9000"));
        // mock clientService
        LoanClientDto mockClient = new LoanClientDto();
        mockClient.setId(100L);
        mockClient.setBirthDate(LocalDate.now().minusYears(26));
        mockClient.setStatus(LoanClientStatus.ACTIVE);

        when(clientService.getClientByPassport("MP", "83456456")).thenReturn(mockClient);

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contractNumber").value("LOAN-NEW"));
    }

    @Test
    void testGetLoanById_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/loans/{id}",testLoan.getLoanId()))     //1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractNumber").value("LOAN-001"));
    }

    @Test
    void testGetAllLoans_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testUpdateLoan_shouldReturnUpdatedData() throws Exception {
        LoanUpdateDto updateDto = new LoanUpdateDto();
        updateDto.setContractNumber("UPDATED-LOAN-001");
        updateDto.setProductType(LoanProductType.CONSUMER_LOAN);
        updateDto.setLoanAmount(new BigDecimal("10000"));
        updateDto.setInterestRate(new BigDecimal("5.00"));
        updateDto.setLoanTermMonths(12);
        updateDto.setEndDate(LocalDate.now().plusYears(1));
        updateDto.setStatus(LoanStatus.ACTIVE);
        updateDto.setCurrentDebt(new BigDecimal("9000"));
        updateDto.setLoanTerms(new LoanTermsDto(true, "No penalty", 30, 12));

        // mock getClientForLoan("MP", "83456456")
        LoanClientDto mockClient = new LoanClientDto();
        mockClient.setId(100L);
        mockClient.setBirthDate(LocalDate.now().minusYears(26));
        mockClient.setStatus(LoanClientStatus.ACTIVE);

        when(clientService.getClientByPassport("MP", "83456456")).thenReturn(mockClient);

        mockMvc.perform(put("/api/loans/{id}", testLoan.getLoanId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractNumber").value("UPDATED-LOAN-001"));
    }

/*    @Test
    void testDeleteLoan_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/loans/{id}", 1L))
                .andExpect(status().isOk());
    }*/

    @Test
    void testGenerateStatement_shouldReturnString() throws Exception {
        mockMvc.perform(get("/api/loans/{id}/statement", testLoan.getLoanId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Loan Statement for Contract Number:")));
    }

    @Test
    void testIssueLoan_shouldReturn200() throws Exception {


        mockMvc.perform(post("/api/loans/{id}/issue", testLoan.getLoanId()))
                .andExpect(status().isOk());
    }

    @Test
    void testProcessPayment_shouldReturn200() throws Exception {

        mockMvc.perform(post("/api/loans/{id}/payment", testLoan.getLoanId())
                        .param("amount", "500"))
                .andExpect(status().isOk());
    }

    @Test
    void testCloseLoan_shouldReturn200() throws Exception {

        testLoan.setCurrentDebt(new BigDecimal("0"));   //все оплачено

        mockMvc.perform(post("/api/loans/{id}/close", testLoan.getLoanId()))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateLoan_withInvalidData_shouldReturn400() throws Exception {
        LoanRequestDto invalidDto = new LoanRequestDto();
        invalidDto.setClientId(null); // обязательное поле
        invalidDto.setLoanAmount(BigDecimal.ZERO); // должно быть > 0

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}