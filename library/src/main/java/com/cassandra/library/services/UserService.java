package com.cassandra.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassandra.library.models.User;
import com.cassandra.library.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        User userToSave = new User(user.getEmail(), user.getName(), user.getPhoneNumber());
        return userRepository.save(userToSave);

    }
}
