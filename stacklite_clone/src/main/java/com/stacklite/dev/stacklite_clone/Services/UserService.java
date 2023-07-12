package com.stacklite.dev.stacklite_clone.Services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Repositories.UsersRepo;
import com.stacklite.dev.stacklite_clone.Dto.UserProfileUpdateDto;
import com.stacklite.dev.stacklite_clone.Handlers.NotFoundException;

@Service
public class UserService {
    @Autowired
    private UsersRepo usersRepo;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // @Value("${DB_NAME}")
    // String dbPasword;

    public Optional<List<User>> allUsers(Map<String, String> queryParameters) {
        if (queryParameters.containsKey("email")) {
            String email = queryParameters.get("email");
            logger.info("email is {}", email);
            return usersRepo.findByEmail(email);
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

}
