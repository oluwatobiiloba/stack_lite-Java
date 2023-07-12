package com.stacklite.dev.stacklite_clone.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAuthDto {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
