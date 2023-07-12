package com.stacklite.dev.stacklite_clone.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    @Value("${SALT_ROUNDS}")
    String salt;

    public String generateSalt() {
        return BCrypt.gensalt(Integer.parseInt(salt));

    }

    public static String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
}
