package com.stacklite.dev.stacklite_clone.Mapper;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.dto.user.UserRespDto;

import java.util.stream.Collectors;

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
