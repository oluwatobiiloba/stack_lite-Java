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
import com.stacklite.dev.stacklite_clone.utils.BlobUploader;
import com.stacklite.dev.stacklite_clone.utils.Pagination;
import com.stacklite.dev.stacklite_clone.utils.PasswordUtils;
import com.stacklite.dev.stacklite_clone.utils.SearchResultBuilder;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UsersRepo usersRepo;

    private final RolesRepo rolesRepo;

    private final BlobUploader blobUploader;

    public UserService(UsersRepo usersRepo, RolesRepo rolesRepo, BlobUploader blobUploader) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
        this.blobUploader = blobUploader;
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


        String usernameParam = queryParameters.get("username");
        Integer phoneNumberParam = queryParameters.containsKey("phoneNumber") ? Integer.parseInt(queryParameters.get("phoneNumber")) : null;
        Integer userIdParam = queryParameters.containsKey("userId") ? Integer.parseInt(queryParameters.get("userId")) : null;
        String firstNameParam = queryParameters.get("firstName");
        String lastNameParam = queryParameters.get("lastName");
        String stackParam = queryParameters.get("stack");
        String emailParam = queryParameters.get("email");

        usersPage = usersRepo.findByParams(
                usernameParam,
                phoneNumberParam,
                userIdParam,
                firstNameParam,
                lastNameParam,
                stackParam,
                emailParam,
                pageable);
        assert usersPage != null;
        users = usersPage.getContent().stream()
                .map(UserMapper::mapToUserRespDto)
                .collect(Collectors.toList());
        return SearchResultBuilder.buildResult(users, usersPage);
    }

    private Page<User> searchUsersByEmail(String email, Pageable pageable) {
        Optional<User> optionalUser = usersRepo.findByEmail(email);
        return Pagination.convertToPage(optionalUser, pageable);
    }

    public Optional<UserRespDto> getUser(Integer Id) {
        return usersRepo.findById(Id).map(UserMapper::mapToUserRespDto);
    }

    public Optional<User> getUserByEmail(String email) {
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

    public void  updateResetToken(String token, Optional<User> userOptional){

        User user = userOptional.orElse(null);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        Timestamp expiryDate = new Timestamp(calendar.getTimeInMillis());//expires after an hour

        assert user != null;
        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiresAt( expiryDate);

        usersRepo.save(user);

    }

    public Optional<User> getUserByResetToken(String token){
        Optional<User> user = usersRepo.findByPasswordResetToken(token);
        if(user.isEmpty() || user.get().tokenHasExpired()){
            throw new NotFoundException("Invalid/Expired Token");
        }
        return  user;
    }

    public  void updatePassword(String password, User user){
            String hashedPassword = PasswordUtils.hashPassword(password);
            user.setPassword(hashedPassword);
            user.setPasswordResetToken(null);
            user.setPasswordResetTokenExpiresAt(null);
            usersRepo.save(user);
    }

    public Optional<User> uploadProfileImage(@NotBlank MultipartFile image, Integer id, String userName) throws IOException {
        InputStream data = image.getInputStream();
        String extension = Optional.ofNullable(image.getContentType())
                .filter(m -> m.matches("^image/(png|jpeg|jpg)$"))
                .map(m -> "." + m.substring(m.lastIndexOf('/') + 1 ).toLowerCase())
                .orElse( "");
        String fileName = userName + "_profile_image";

        if (extension.isBlank()) throw new IllegalArgumentException("Invalid file, ensure you are uploading an image");

       String imageUrl = blobUploader.uploadBlob("image",data,image.getSize(),fileName,extension);

       Optional<User> optionalUser = usersRepo.findById(id);
       if(optionalUser.isPresent()){
           User user = optionalUser.get();
           user.setProfileImage(imageUrl);
           usersRepo.save(user);
           return Optional.of(user);
       }else {
           throw new NotFoundException("User Not Found/Deleted");
       }

    }
}
