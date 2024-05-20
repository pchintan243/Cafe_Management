package com.cafe.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.server.dao.UserDao;
import com.cafe.server.entities.User;
import com.cafe.server.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signUp(UserDao userDao) {
        User existingUser = userRepository.findByEmail(userDao.getEmail());
        if (existingUser != null) {
            return null;
        }
        User user = new User();
        user.setName(userDao.getName());
        user.setEmail(userDao.getEmail());
        user.setPassword(userDao.getPassword());
        user.setContactNumber(userDao.getContactNumber());
        user.setRole("user");
        user.setStatus("false");
        return userRepository.save(user);
    }
}
