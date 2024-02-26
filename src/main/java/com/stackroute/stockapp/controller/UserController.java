package com.stackroute.stockapp.controller;
/*
 * autowire UserService, create methods for the methods available in service method
 */

import com.stackroute.stockapp.exceptions.UserAlreadyExistException;
import com.stackroute.stockapp.model.User;
import com.stackroute.stockapp.service.UserService;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> saveUser(@RequestBody User user) throws UserAlreadyExistException {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> validateUser(@RequestBody User user) {
        User user1= userService.validateUser(user.getEmailId(), user.getPassword());
        if(user1==null) {
            return new ResponseEntity<String>("Invalid User", HttpStatus.NOT_FOUND);
        }   
        String token = getToken(user.getEmailId());
        return new ResponseEntity<String>("Logged In Successfully.. Token:"+token, HttpStatus.OK);
    }

    //create method gettoken to generate token based on emailId
     private String getToken(String emailId) {
        return Jwts.builder()
        .setIssuedAt(new Date())
        .setSubject(emailId)
        .signWith( io.jsonwebtoken.SignatureAlgorithm.HS256,"Success")
        .compact();
     }
      

        
}