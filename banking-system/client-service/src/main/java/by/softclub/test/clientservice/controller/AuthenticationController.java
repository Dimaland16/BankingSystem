package by.softclub.test.clientservice.controller;

import by.softclub.test.clientservice.dto.client.ClientRequestDto;
import by.softclub.test.clientservice.dto.client.ClientResponseDto;
import by.softclub.test.clientservice.service.AuthenticationService;
import by.softclub.test.clientservice.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/clients")
public class AuthenticationController {

    private final ClientService clientService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(ClientService clientService, AuthenticationService authenticationService) {
        this.clientService = clientService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ClientResponseDto> registerClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.createClient(clientRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String token = authenticationService.loginClient(credentials.get("email"), credentials.get("password"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}
