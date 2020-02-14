package com.framework.registration.Service;

import com.framework.registration.Entity.User;
import com.framework.registration.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Iterable<User> findAllUsers(){
       return userRepository.findAll();
    }

    public Optional<User> findUser(Long id){
        return userRepository.findById(id);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }
}
