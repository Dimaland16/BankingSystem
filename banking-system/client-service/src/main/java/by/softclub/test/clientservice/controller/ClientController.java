package by.softclub.test.clientservice.controller;


import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.dto.client.ClientUpdateDto;
import by.softclub.test.clientservice.dto.clientChangeHistory.ClientChangeHistoryResponseDto;
import by.softclub.test.clientservice.entity.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.softclub.test.clientservice.service.ClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createClient(clientRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> registerClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.createClient(clientRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String token = clientService.loginClient(email, password);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

   @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateDto updateDto) {
        return ResponseEntity.ok(clientService.updateClient(id, updateDto));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<ClientStatus> getClientStatus(@PathVariable Long id) {
        ClientStatus status = clientService.getClientStatus(id);
        return ResponseEntity.ok(status);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeClientStatus(
            @PathVariable Long id,
            @RequestParam ClientStatus newStatus) {
        clientService.changeClientStatus(id, newStatus);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<ClientChangeHistoryResponseDto>> getClientChangeHistory(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientChangeHistory(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientResponseDto>> searchClients(
            @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName, @RequestParam(required = false) LocalDate birthDate,
            @RequestParam(required = false) LocalDate birthDateStart, @RequestParam(required = false) LocalDate birthDateEnd,
            @RequestParam(required = false) String passportSeries, @RequestParam(required = false) String passportNumber,
            @RequestParam(required = false) String email, @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) ClientStatus status, @RequestParam(required = false) LocalDateTime registrationDateStart,
            @RequestParam(required = false) LocalDateTime registrationDateEnd) {

        List<ClientResponseDto> clients = clientService.searchClients(
                firstName,
                lastName,
                middleName,
                birthDate,
                birthDateStart,
                birthDateEnd,
                passportSeries,
                passportNumber,
                email,
                phoneNumber,
                status,
                registrationDateStart,
                registrationDateEnd
        );

        return ResponseEntity.ok(clients);
    }
}
