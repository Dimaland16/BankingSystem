package by.softclub.test.clientservice.controller;


import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.dto.client.ClientUpdateDto;
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

    @GetMapping("/{clientId}/status")
    public ResponseEntity<ClientStatus> getClientStatus(@PathVariable Long clientId) {
        ClientStatus status = clientService.getClientStatus(clientId);
        return ResponseEntity.ok(status);
    }

    @PatchMapping("/{clientId}/status")
    public ResponseEntity<Void> changeClientStatus(
            @PathVariable Long clientId,
            @RequestParam ClientStatus newStatus) {
        clientService.changeClientStatus(clientId, newStatus);
        return ResponseEntity.noContent().build();
    }
/*


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


    @GetMapping("/{id}/history")
    public ResponseEntity<List<ClientResponseDto>> getClientHistory(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientHistory(id));
    }*/
}
