package com.stacklite.dev.stacklite_clone.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateDto {

    // @NotBlank(message = "First name is required")
    private String firstName;

    // @NotBlank(message = "Last name is required")
    private String lastName;

    private Long phoneNumber;

    private String email;

    private String profileImage;

    private String meta;

    private String age;

    private String nationality;

}
