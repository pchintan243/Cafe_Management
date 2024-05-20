package com.cafe.server.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cafe.server.entities.JwtResponse;
import com.cafe.server.entities.Login;
import com.cafe.server.entities.User;
import com.cafe.server.entities.UserDao;
import com.cafe.server.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@SecurityRequirement(name = "BearerAuth")
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

    @PostMapping("/user/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody Login login) {

        JwtResponse response = userService.userLogin(login);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
