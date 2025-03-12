package com.laborexport.pet_supermarket.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username is required")
    @NotBlank(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is required")
    private String password;
}
