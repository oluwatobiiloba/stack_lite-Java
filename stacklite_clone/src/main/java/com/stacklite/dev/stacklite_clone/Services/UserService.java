package com.stacklite.dev.stacklite_clone.Services;

import com.stacklite.dev.stacklite_clone.Mapper.UserMapper;
import com.stacklite.dev.stacklite_clone.Model.ERole;
import com.stacklite.dev.stacklite_clone.Model.Role;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Repositories.RolesRepo;
import com.stacklite.dev.stacklite_clone.Repositories.UsersRepo;
import com.stacklite.dev.stacklite_clone.dto.user.UserProfileUpdateDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserRegistrationDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserRespDto;
import com.stacklite.dev.stacklite_clone.handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.utils.Pagination;
import com.stacklite.dev.stacklite_clone.utils.SearchResultBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UsersRepo usersRepo;

    private final RolesRepo rolesRepo;

    public UserService(UsersRepo usersRepo, RolesRepo rolesRepo) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
    }

    public Map<String, Object> allUsers(Map<String, String> queryParameters) {

        Pageable pageable = Pagination.createPageable(queryParameters);
        Page<User> usersPage = usersRepo.findAll(pageable);
        List<UserRespDto> users = usersPage.getContent().stream()
                .map(UserMapper::mapToUserRespDto)
                .collect(Collectors.toList());

        return SearchResultBuilder.buildResult(users, usersPage);
    }

    public Map<String, Object> searchUsers(Map<String, String> queryParameters) {
        if (queryParameters.isEmpty()) {
            throw new NotFoundException("No search params found");
        }

        Pageable pageable = Pagination.createPageable(queryParameters);
        List<UserRespDto> users = null;
        Page<User> usersPage = null;

        if (queryParameters.containsKey("email")) {
            String email = queryParameters.get("email");
            usersPage = searchUsersByEmail(email, pageable);
            users = usersPage.getContent().stream()
                    .map(UserMapper::mapToUserRespDto)
                    .collect(Collectors.toList());
        }

        if (queryParameters.containsKey("age")) {
            String age = queryParameters.get("age");
            usersPage = searchUsersByAge(age, pageable);
            users = usersPage.getContent().stream()
                    .map(UserMapper::mapToUserRespDto)
                    .collect(Collectors.toList());

        }

        return SearchResultBuilder.buildResult(users, usersPage);
    }

    private Page<User> searchUsersByEmail(String email, Pageable pageable) {
        // Implement the logic to search users by email using your repository or service
        // methods
        Optional<User> optionalUser = usersRepo.findByEmail(email);
        return Pagination.convertToPage(optionalUser, pageable);
    }

    private Page<User> searchUsersByAge(String age, Pageable pageable) {
        // Implement the logic to search users by age using your repository or service
        // methods
        return usersRepo.findByAge(age, pageable);
    }

    public Optional<UserRespDto> getUser(Integer Id) {
        return usersRepo.findById(Id).map(UserMapper::mapToUserRespDto);
    }

    public Optional<User> getUserbyEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    public Optional<User> updateProfile(Integer Id, UserProfileUpdateDto userUpdateDTO) {
        Optional<User> optionalUser = usersRepo.findById(Id);

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
            for (String role : strRoles) {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = rolesRepo.findByName(ERole.ROLE_ADMIN).orElseThrow(
                                () -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = rolesRepo.findByName(ERole.ROLE_MANAGER).orElseThrow(
                                () -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = rolesRepo.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            }
        }

        user.setAuthorities(roles);

        return Optional.of(usersRepo.save(user));
    }

    public Boolean checkEmailAvailability(String email) {
        return usersRepo.existsByEmail(email);
    }

    public Boolean checkUsernameAvailability(String username) {
        return usersRepo.existsByUsername(username);
    }
}
