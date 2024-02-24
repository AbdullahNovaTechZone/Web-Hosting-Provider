package com.novatechzone.web.domain.security.rest;

import com.novatechzone.web.domain.security.dto.LogInDTO;
import com.novatechzone.web.domain.security.dto.LogInResponseDTO;
import com.novatechzone.web.domain.security.dto.UserDTO;
import com.novatechzone.web.domain.security.service.AuthService;
import com.novatechzone.web.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<ApplicationResponseDTO> signup(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.signup(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponseDTO> login(@Valid @RequestBody LogInDTO logInDTO) {
        return ResponseEntity.ok(authService.login(logInDTO));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refreshAccessToken() {
        return ResponseEntity.ok(authService.generateRefreshToken());
    }
}
