package com.stacklite.dev.stacklite_clone.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Model.ERole;
import com.stacklite.dev.stacklite_clone.Model.Role;
import com.stacklite.dev.stacklite_clone.Repositories.RolesRepo;
import com.stacklite.dev.stacklite_clone.Repositories.UsersRepo;
import com.stacklite.dev.stacklite_clone.Dto.UserProfileUpdateDto;
import com.stacklite.dev.stacklite_clone.Dto.UserRegistrationDto;
import com.stacklite.dev.stacklite_clone.Handlers.NotFoundException;

@Service
public class UserService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private RolesRepo rolesRepo;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // @Value("${DB_NAME}")
    // String dbPasword;

    public Optional<List<User>> allUsers(Map<String, String> queryParameters) {
        if (queryParameters.containsKey("email")) {
            String email = queryParameters.get("email");
            logger.info("email is {}", email);
            Optional<User> optionalUser = usersRepo.findByEmail(email);
            if (optionalUser.isPresent()) {
                List<User> userList = Collections.singletonList(optionalUser.get());
                return Optional.of(userList);
            } else {
                return Optional.empty();
            }
        } else {
            List<User> users = usersRepo.findAll();
            Optional<List<User>> optionalUsers = Optional.ofNullable(users);
            return optionalUsers;
        }

    }

    // public List<User> findUsers(String queryString) {

    // }

    public Optional<User> getUser(Integer Id) {
        return usersRepo.findById(Id);
    }

    public Optional<User> getUserbyEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    public Optional<User> updateProfile(Integer Id, UserProfileUpdateDto userUpdateDTO) throws Exception {
        Optional<User> optionalUser = usersRepo.findById(Id);

        System.out.println(optionalUser);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found with ID: " + Id);
        }

        User user = optionalUser.get();

        if (userUpdateDTO.getFirstName() != null) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }

        if (userUpdateDTO.getLastName() != null) {
            user.setLastName(userUpdateDTO.getLastName());
        }

        if (userUpdateDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }

        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }

        if (userUpdateDTO.getProfileImage() != null) {
            user.setProfileImage(userUpdateDTO.getProfileImage());
        }

        User updatedUser = usersRepo.save(user);
        return Optional.of(updatedUser);
    }

    public Optional<User> createUser(UserRegistrationDto userRegDto) {

        User user = new User();
        user.setUsername(userRegDto.getUsername());
        user.setFirstName(userRegDto.getFirstName());
        user.setLastName(userRegDto.getLastName());
        user.setPhoneNumber(userRegDto.getPhoneNumber());
        user.setEmail(userRegDto.getEmail());
        user.setPassword(userRegDto.getPassword());

        Set<String> strRoles = userRegDto.getRole();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            Role userRole = rolesRepo.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                System.out.println(role);
                switch (role) {
                    case "admin":
                        Role adminRole = rolesRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(
                                () -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = rolesRepo.findByName(ERole.ROLE_MANAGER).orElseThrow(
                                () -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = rolesRepo.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setAuthorities(roles);

        User createdUser = usersRepo.save(user);

        return Optional.of(createdUser);
    }

}
