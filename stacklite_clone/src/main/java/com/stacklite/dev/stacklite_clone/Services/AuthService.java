package com.stacklite.dev.stacklite_clone.Services;

import com.stacklite.dev.stacklite_clone.dto.user.UserAuthDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserRegistrationDto;
import com.stacklite.dev.stacklite_clone.handlers.UnauthorizedException;
import com.stacklite.dev.stacklite_clone.Model.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    UserService userService;

    private final EmailTemplateService azureMailer;

    final Dotenv dotenv = Dotenv.load();
    private final String liveBaseUrl = dotenv.get("LIVE_URL_BASE");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AuthService(EmailTemplateService azureMailer) {
        this.azureMailer = azureMailer;
    }

    @Autowired
    public AuthService(UserService userService, EmailTemplateService azureMailer) {
        this.userService = userService;
        this.azureMailer = azureMailer;
    }

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
        }

        if (isValidPassword) {
            return user;
        } else {
            throw new UnauthorizedException("Invalid Email/Password");
        }

    }

    public Optional<User> register(UserRegistrationDto userRegDto) {
        Optional<User> createdUser = userService.createUser(userRegDto);

        if (createdUser.isPresent()) {
            Map<String, String> constants = new HashMap<>();

            String username = userRegDto.getUsername();
            String subject = "Welcome to Stacklite";
            String email = userRegDto.getEmail();
            String verification_link = liveBaseUrl + "/api/v1/users/verify-email?token=${verification_token}";
            constants.put("username", username);
            constants.put("verification_link", verification_link);

            azureMailer.sendEmailWithTemplate(1, constants, email, subject);
        }
        return createdUser;
    }
}
