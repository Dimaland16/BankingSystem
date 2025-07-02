package by.softclub.test.deposit_service.service;

import by.softclub.test.deposit_service.dto.DepositClientDto;
import by.softclub.test.deposit_service.dto.DepositClientStatus;
import by.softclub.test.deposit_service.exception.ClientNotFoundException;
import by.softclub.test.deposit_service.exception.ClientServiceNotFoundException;
import by.softclub.test.deposit_service.exception.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    public ClientService(RestClient.Builder builder, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        String baseUrl = getClientServiceUrl();
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    private String getClientServiceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances("client-service");
        if (instances.isEmpty()) {
            throw new ClientServiceNotFoundException("No instances of client-service found in Eureka");
        }
        ServiceInstance instance = instances.getFirst();
        return instance.getUri().toString() + "/api/v1.0/clients";
    }

    public DepositClientDto getClientByPassport(String passportSeries, String passportNumber) {
        try {
            List<Map<String, Object>> clients = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("passportSeries", passportSeries)
                            .queryParam("passportNumber", passportNumber)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            if (clients == null || clients.isEmpty()) {
                throw new ClientNotFoundException("Client not found with provided passport data: series=" + passportSeries + ", number=" + passportNumber);
            }

            return mapToLoanClientDto(clients.getFirst());
        } catch (Exception e) {
            throw e;
        }
    }

    private DepositClientDto mapToLoanClientDto(Map<String, Object> clientData) {
        try {
            DepositClientDto loanClientDto = new DepositClientDto();
            loanClientDto.setId(((Number) clientData.get("id")).longValue());
            loanClientDto.setFirstName((String) clientData.get("firstName"));
            loanClientDto.setLastName((String) clientData.get("lastName"));
            loanClientDto.setMiddleName((String) clientData.get("middleName"));
            loanClientDto.setBirthDate(LocalDate.parse(clientData.get("birthDate").toString()));

            String status = (String) clientData.get("status");
            if (status != null) {
                loanClientDto.setStatus(DepositClientStatus.valueOf(status));
            }

            return loanClientDto;
        } catch (Exception e) {
            throw new MappingException("Failed to map client data: " + e.getMessage());
        }
    }
}
