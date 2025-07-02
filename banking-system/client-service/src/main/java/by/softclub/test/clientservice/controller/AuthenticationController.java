package by.softclub.test.clientservice.controller;

import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.service.AuthenticationService;
import by.softclub.test.clientservice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/clients")
@Tag(name = "Контроллер аутентификации", description = "Операции по аутентификации и регистрации клиентов")
public class AuthenticationController {

    private final ClientService clientService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(ClientService clientService, AuthenticationService authenticationService) {
        this.clientService = clientService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация клиента",
            description = "Метод регистрации клиента, возвращает данные о зарегистрированном клиенте"
    )
    public ResponseEntity<ClientResponseDto> registerClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.createClient(clientRequestDto));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Вход клиента в систему",
            description = "Аутентификация клиента по email и password"
    )
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String token = authenticationService.loginClient(credentials.get("email"), credentials.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}
