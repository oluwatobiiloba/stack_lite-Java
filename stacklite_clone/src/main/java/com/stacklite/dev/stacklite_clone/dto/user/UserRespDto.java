package com.stacklite.dev.stacklite_clone.dto.user;

import java.util.List;

public record UserRespDto(

                Integer id,

                String uuid,

                String username,

                String firstName,

                String lastName,

                Long phoneNumber,

                String email,

                Boolean isVerified,

                String stack,

                String profileImage,

                String meta,

                String age,

                String nationality,

                List<String> authorities

) {

}
