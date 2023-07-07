package com.stacklite.dev.stacklite_clone.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test12345@gmail.com");
        user.setPassword("test202020");
        user.setFirstName("Ravi");
        user.setLastName("Kumar");
        user.setUsername("tetsingJava");
        user.setIsVerified(false);
        user.setPhoneNumber((long) 810223455);
        user.setRole(2);
        // Date date = new Date(0);

        // user.setCreatedAt(date);
        // user.setUpdatedAt(date);

        User savedUser = userRepo.save(user);

        System.out.println(savedUser);

        User existUser = entityManager.find(User.class, savedUser.getId());

        System.out.println(existUser);
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}
