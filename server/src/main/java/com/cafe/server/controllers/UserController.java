package com.cafe.server.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cafe.server.dao.UserDao;
import com.cafe.server.entities.User;
import com.cafe.server.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<User> signUp(@RequestBody UserDao userDao) {
        try {
            User newUser = userService.signUp(userDao);
            if (newUser == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
