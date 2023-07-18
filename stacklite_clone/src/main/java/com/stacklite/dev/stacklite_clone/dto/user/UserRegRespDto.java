package com.stacklite.dev.stacklite_clone.dto.user;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegRespDto {
    private Optional<UserRespDto> user;
    private String token;
}