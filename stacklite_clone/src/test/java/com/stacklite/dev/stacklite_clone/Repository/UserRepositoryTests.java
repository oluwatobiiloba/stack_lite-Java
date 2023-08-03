package com.stacklite.dev.stacklite_clone.Repository;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Repositories.UsersRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepo userRepo;

    @Test
    public void Test_Create_User() {
        User user = createUser1();

        User savedUser = userRepo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
        assertThat(existUser.getPassword().isBlank() && existUser.getPassword().contains("test202020")).isFalse();
        assertThat(existUser.getIsVerified()).isFalse();
    }

    @Test
    public void Test_Get_All_Users() {
        User user1 = createUser1();

        User user2 = createUser2();

        userRepo.save(user1);
        userRepo.save(user2);

        List<User> userList= userRepo.findAll();

        assertThat(userList).isNotEmpty();
        assertThat(userList).contains(user1, user2);
    }

    @Test
    public void Test_Find_By_Id() {
        User user = createUser1();
        userRepo.save(user);

        Optional<User> foundUser = userRepo.findById(user.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void Test_Find_By_Username() {
        User user = createUser1();
        userRepo.save(user);

        Optional<User> foundUser = userRepo.findByUsername(user.getUsername());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void Test_Find_By_Email() {
        User user = createUser1();
        userRepo.save(user);

        Optional<User> foundUser = userRepo.findByEmail(user.getEmail());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void Test_Find_By_Stack() {
        User user = createUser1();
        userRepo.save(user);

        Pageable pageable = PageRequest.of(0, 10);
        Page<User> foundUsers = userRepo.findByStack(user.getStack(), pageable);

        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.getContent()).contains(user);
    }

    @Test
    public void Test_Delete_User() {
        User user = createUser1();
        userRepo.save(user);

        userRepo.delete(user);

        Optional<User> foundUser = userRepo.findById(user.getId());
        assertThat(foundUser).isEmpty();
    }

    @Test
    public void Test_Update_User() {
        User user = createUser1();
        userRepo.save(user);

        Optional<User> savedUser = userRepo.findById(user.getId());

        savedUser.get().setProfileImage("test.jpg");
        savedUser.get().setUsername("test");

        User updatedUser = userRepo.save(savedUser.get());

        assertThat(updatedUser.getProfileImage()).isEqualTo("test.jpg");
        assertThat(updatedUser.getUsername()).isEqualTo("test");

    }


    private User createUser1(){
        return User.builder()
                .email("test523@gmail.com")
                .password("test202020")
                .firstName("Oluwatobiloba2")
                .lastName("Aremu2")
                .stack("WebDesign")
                .username("testingJava2")
                .isVerified(false)
                .phoneNumber((long) 810223466)
                .build();
    }

    private User createUser2(){
        return User.builder()
                .email("test12345@gmail.com")
                .password("test202020")
                .firstName("Oluwatobiloba")
                .lastName("Aremu")
                .username("testingJava")
                .isVerified(false)
                .phoneNumber((long) 810223455)
                .build();
    }


}
