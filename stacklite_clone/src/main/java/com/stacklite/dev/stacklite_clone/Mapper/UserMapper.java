package com.stacklite.dev.stacklite_clone.Mapper;

import java.util.stream.Collectors;

import com.stacklite.dev.stacklite_clone.dto.user.*;
import com.stacklite.dev.stacklite_clone.Model.User;

public class UserMapper {
    public static UserRespDto mapToUserRespDto(User user) {
        return new UserRespDto(
                user.getId(),
                user.getUuid(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getIsVerified(),
                user.getStack(),
                user.getProfileImage(),
                user.getMeta(),
                user.getAge(),
                user.getNationality(),
                user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
    }

}
