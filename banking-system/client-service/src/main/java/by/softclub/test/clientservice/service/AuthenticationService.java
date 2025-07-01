package by.softclub.test.clientservice.service;

import by.softclub.test.clientservice.entity.Client;
import by.softclub.test.clientservice.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationService {

    private final JwtEncoder jwtEncoder;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(JwtEncoder jwtEncoder, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateToken(Long userId, String email) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null or empty");
            }

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .subject(email)
                    .claim("userId", userId)
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(3600))
                    .build();

            JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS512).build();
            JwtEncoderParameters parameters = JwtEncoderParameters.from(jwsHeader, claims);

            String token = jwtEncoder.encode(parameters).getTokenValue();
            return token;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input for token generation: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Failed to generate token: " + e.getMessage());
            throw new JwtEncodingException("Failed to generate token due to an internal error", e);
        }
    }

    public String loginClient(String email, String password) {
        try {
            if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Email and password cannot be null or empty");
            }

            Client client = (Client) clientRepository.findByContactInfoEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Клиент с email " + email + " не найден"));

            if (!passwordEncoder.matches(password, client.getPassword())) {
                throw new IllegalArgumentException("Неверный пароль для email " + email);
            }

            return generateToken(client.getId(), email);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            throw new RuntimeException("An unexpected error occurred during login", e);
        }
    }
}
