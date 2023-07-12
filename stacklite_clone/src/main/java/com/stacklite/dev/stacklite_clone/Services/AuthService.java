package com.stacklite.dev.stacklite_clone.Services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stacklite.dev.stacklite_clone.Dto.UserAuthDto;
import com.stacklite.dev.stacklite_clone.Dto.UserRegistrationDto;
import com.stacklite.dev.stacklite_clone.Handlers.UnauthorizedException;
import com.stacklite.dev.stacklite_clone.Model.User;

@Service
public class AuthService {

    // @Value("${SALT_ROUNDS}")
    // String salt;

    @Autowired

    UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Optional<User> authenticate(UserAuthDto userAuthDto) {
        Optional<User> user;

        boolean isValidEmail = false;
        boolean isValidPassword = false;
        user = userService.getUserbyEmail(userAuthDto.getEmail());
        if (user.isPresent()) {
            isValidEmail = true;
            String password = user.get().getPassword();
            String salt = password.substring(0, 20);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            isValidPassword = encoder.matches(userAuthDto.getPassword(),
                    salt + password.substring(20));
            logger.info(user.toString());
        }

        if (isValidPassword && isValidEmail) {
            logger.info(user.toString());
            return user;
        } else {
            throw new UnauthorizedException("Invalid Email/Password");
        }

    }

    public Optional<User> register(UserRegistrationDto userRegDto) {
        return userService.createUser(userRegDto);
    }
}
