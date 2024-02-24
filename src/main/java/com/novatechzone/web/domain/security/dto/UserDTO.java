package com.novatechzone.web.domain.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
}
