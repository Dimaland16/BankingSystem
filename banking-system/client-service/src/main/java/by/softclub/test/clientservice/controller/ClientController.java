package by.softclub.test.clientservice.controller;


import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.dto.client.ClientUpdateDto;
import by.softclub.test.clientservice.dto.clientChangeHistory.ClientChangeHistoryResponseDto;
import by.softclub.test.clientservice.entity.*;
import by.softclub.test.clientservice.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.softclub.test.clientservice.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/clients")
@Tag(name = "Клиентский Контроллер", description = "CRUD операции по работе с клиентами и операции поиска по фильтрам")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService, AuthenticationService authenticationService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(
            summary = "Создать нового клиента",
            description = "Создаёт нового клиента на основе переданных данных"
    )
    public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createClient(clientRequestDto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить клиента по ID",
            description = "Возвращает данные клиента по его уникальному идентификатору"
    )
    public ResponseEntity<ClientResponseDto> getClientById(
            @PathVariable
            @Parameter(description = "Идентификатор клиента", example = "1") Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

   @GetMapping
   @Operation(
           summary = "Получить всех клиентов",
           description = "Возвращает список всех клиентов"
   )
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить клиента по Идентификатору",
            description = "Удаляет клиента из системы"
    )
    public ResponseEntity<Void> deleteClient(
            @PathVariable
            @Parameter(description = "Идентификатор клиента для удаления", example = "1") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные клиента",
            description = "Обновляет данные клиента по указанному идентификатору"
    )
    public ResponseEntity<ClientResponseDto> updateClient(
            @PathVariable
            @Parameter(description = "Идентификатор клиента для обновления", example = "1") Long id,
            @Valid @RequestBody ClientUpdateDto updateDto) {
        return ResponseEntity.ok(clientService.updateClient(id, updateDto));
    }

    @GetMapping("/{id}/status")
    @Operation(
            summary = "Получить статус клиента",
            description = "Возвращает текущий статус клиента (ACTIVE, BLOCKED, SUSPENDED)"
    )
    public ResponseEntity<ClientStatus> getClientStatus(@PathVariable Long id) {
        ClientStatus status = clientService.getClientStatus(id);
        return ResponseEntity.ok(status);
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Изменить статус клиента",
            description = "Изменяет статус клиента (ACTIVE, BLOCKED, SUSPENDED)"
    )
    public ResponseEntity<Void> changeClientStatus(
            @PathVariable
            @Parameter(description = "Идентификатор клиента", example = "1") Long id,
            @RequestParam
            @Parameter(description = "Новый статус клиента", example = "ACTIVE") ClientStatus newStatus) {
        clientService.changeClientStatus(id, newStatus);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    @Operation(
            summary = "Получить историю изменений клиента",
            description = "Возвращает список записей истории изменений клиента"
    )
    public ResponseEntity<List<ClientChangeHistoryResponseDto>> getClientChangeHistory(
            @PathVariable
            @Parameter(description = "Идентификатор клиента", example = "1") Long id) {
        return ResponseEntity.ok(clientService.getClientChangeHistory(id));
    }

    @GetMapping("/search")
    @Operation(
            summary = "Поиск клиентов по фильтрам",
            description = "Фильтрует клиентов по имени, дате рождения, паспорту и тд."
    )
    public ResponseEntity<List<ClientResponseDto>> searchClients(
            @RequestParam(required = false)
            @Parameter(description = "Имя клиента", example = "Иван") String firstName,

            @RequestParam(required = false)
            @Parameter(description = "Фамилия клиента", example = "Иванов") String lastName,

            @RequestParam(required = false)
            @Parameter(description = "Отчество клиента", example = "Иванович") String middleName,

            @RequestParam(required = false)
            @Parameter(description = "Дата рождения клиента", example = "2005-02-07") LocalDate birthDate,

            LocalDate birthDateStart,

            LocalDate birthDateEnd,

            @RequestParam(required = false)
            @Parameter(description = "Серия паспорта", example = "MP") String passportSeries,

            @RequestParam(required = false)
            @Parameter(description = "Номер паспорта", example = "83456456") String passportNumber,

            @RequestParam(required = false)
            @Parameter(description = "Email клиента", example = "ivanov@example.com") String email,

            @RequestParam(required = false)
            @Parameter(description = "Номер телефона", example = "+375291234567") String phoneNumber,

            @RequestParam(required = false)
            @Parameter(description = "Статус клиента", example = "ACTIVE") ClientStatus status,

            @RequestParam(required = false)
            @Parameter(description = "Начало периода регистрации", example = "2025-01-01T00:00:00") LocalDateTime registrationDateStart,

            @RequestParam(required = false)
            @Parameter(description = "Конец периода регистрации", example = "2025-12-31T23:59:59") LocalDateTime registrationDateEnd) {

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
