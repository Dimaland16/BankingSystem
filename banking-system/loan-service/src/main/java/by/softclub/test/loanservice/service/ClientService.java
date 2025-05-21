package by.softclub.test.loanservice.service;

import by.softclub.test.loanservice.dto.LoanClientDto;
import by.softclub.test.loanservice.dto.LoanClientStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    private final RestClient restClient;

    public ClientService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://169.254.176.87:8081/api/v1.0/clients")
                .build();
    }

    public LoanClientDto getClientByPassport(String passportSeries, String passportNumber) {

        List<Map<String, Object>> clients = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("passportSeries", passportSeries)
                        .queryParam("passportNumber", passportNumber)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (clients == null || clients.isEmpty()) {
            throw new RuntimeException("Client not found with provided passport data");
        }

        return mapToLoanClientDto(clients.getFirst());
    }

    private LoanClientDto mapToLoanClientDto(Map<String, Object> clientData) {
        LoanClientDto loanClientDto = new LoanClientDto();
        loanClientDto.setId(((Number) clientData.get("id")).longValue());
        loanClientDto.setFirstName((String) clientData.get("firstName"));
        loanClientDto.setLastName((String) clientData.get("lastName"));
        loanClientDto.setMiddleName((String) clientData.get("middleName"));
        loanClientDto.setBirthDate((LocalDate.parse(clientData.get("birthDate").toString())));

        String status = (String) clientData.get("status");
        if (status != null) {
            loanClientDto.setStatus(LoanClientStatus.valueOf(status));
        }

        return loanClientDto;
    }
}
