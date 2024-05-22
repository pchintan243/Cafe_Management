package com.cafe.server.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.cafe.server.entities.ChangePassword;
import com.cafe.server.entities.JwtResponse;
import com.cafe.server.entities.Login;
import com.cafe.server.entities.User;
import com.cafe.server.entities.UserDao;
import com.cafe.server.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/api/getAllUser")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = userService.getAllUser();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/user/updateStatus/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable int id) {
        userService.updateStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/forgotPassword")
    public ResponseEntity<Login> forgotPassword(@RequestParam String email) {
        Login user = userService.forgotPassword(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/user/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody ChangePassword changePassword) {
        User updatedUser = userService.changePassword(changePassword);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
