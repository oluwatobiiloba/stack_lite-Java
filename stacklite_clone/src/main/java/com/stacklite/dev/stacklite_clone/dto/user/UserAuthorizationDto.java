package com.stacklite.dev.stacklite_clone.dto.user;

import com.stacklite.dev.stacklite_clone.Model.User;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizationDto {
    private Optional<User> user;
    private String Token;
}