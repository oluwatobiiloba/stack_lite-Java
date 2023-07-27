package com.stacklite.dev.stacklite_clone.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Please provide the token")
    private String token;
}
