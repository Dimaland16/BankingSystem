package by.softclub.test.clientservice.controller;


import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.entity.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.softclub.test.clientservice.service.ClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/clients")
public class ClientController {

    private final ClientService clientService;

    //Создает и вносит нового клиента в бд

    @PostMapping
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.createClient clientRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        return ResponseEntity.ok(clientService.createClient)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    //находит клиентов по различным параметрам
    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getClients (
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
            @RequestParam(required = false) ClientStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime registrationDate,
            @RequestParam(required = false) PassportData passportData,
            @RequestParam(required = false) ContactInfo contactInfo,
            @RequestParam(required = false) Address address
    ) {
        return ResponseEntity.ok(clientService.getClients(firstName, lastName, middleName, birthDate, status, registrationDate,
                passportData, contactInfo, address));
    }



    // Управление статусами клиентов
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClientResponseDto> updateClientStatus(
            @PathVariable Long id,
            @RequestParam ClientStatus newStatus) {
        return ResponseEntity.ok(clientService.updateClientStatus(id, newStatus));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<ClientResponseDto>> getClientHistory(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientHistory(id));
    }
}
