package com.moviecollection.services;

import com.moviecollection.models.User;
import com.moviecollection.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void createUser(User user) throws Exception {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) throw new Exception("User with this email already exists");
        this.userRepository.save(user);
    }

    public User verifyUser(String email, String password) throws Exception {
        User user = this.userRepository.findByEmailAndPassword(email, password);
        if(user == null) throw new Exception("Email or password is not correct!");
        return user;
    }
}
