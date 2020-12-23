package com.cognitree.sangeet.contactServer.Service;

import com.cognitree.sangeet.contactServer.Model.User;
import com.cognitree.sangeet.contactServer.Repository.DatabaseRepository;

import java.util.List;

public class UserService {

    private final DatabaseRepository userRepository = new DatabaseRepository();

    public User getUser(long id) { return this.userRepository.getUser(id); }

    public List<User> getAllUser() {
        return this.userRepository.getAllUsers();
    }
}