package com.stacklite.dev.stacklite_clone.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Repositories.Users;

@Service
public class UserService {
    @Autowired
    private Users users;

    public List<User> allUsers() {
        return users.findAll();
    }

    public Optional<User> getUser(Integer Id) {
        return users.findById(Id);
    }
}
