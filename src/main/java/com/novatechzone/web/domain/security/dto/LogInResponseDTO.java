package com.novatechzone.web.domain.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class LogInResponseDTO {
    private HttpStatus status;
    private String code;
    private String message;
    private String accessToken;
    private String refreshToken;
}