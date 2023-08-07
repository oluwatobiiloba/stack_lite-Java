package com.stacklite.dev.stacklite_clone.Controller;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Repositories.UsersRepo;
import com.stacklite.dev.stacklite_clone.Services.UserService;
import com.stacklite.dev.stacklite_clone.controllers.UserController;
import com.stacklite.dev.stacklite_clone.handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.utils.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepo userRepo;

    @MockBean
    private UserService userService;

    @MockBean
    private ResponseHandler responseHandler;

    @MockBean
    private ResponseUtil responseUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllUsers_Should_ReturnJsonArray() throws Exception {
        User user = createUser1();
        userRepo.save(user);
        List<User> allUsers = Collections.singletonList(user);

        when(userRepo.findAll()).thenReturn(allUsers);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/allusers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
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
                .phoneNumber(810223466L)
                .build();
    }
}
