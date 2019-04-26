package com.micavanco.bookshelf.controller;

import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.UserRepository;
import com.micavanco.bookshelf.service.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserServiceImpl userService, PasswordEncoder passwordEncoder)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<User> getUser(@RequestParam(value = "username")String username,
                                        @RequestParam(value = "password")String password)
    {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        try {
            userService.addUser(user);
        }catch (Exception ex)
        {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return !username.isEmpty() ? new ResponseEntity<User>(user, HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<UserDetails> getUser(@RequestParam(value = "username")String username)
    {
        UserDetails user;
        try {
            user = userService.loadUserByUsername(username);
        }catch (Exception ex)
        {
            return new ResponseEntity<UserDetails>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user != null ? new ResponseEntity<UserDetails>(user, HttpStatus.OK)
                : new ResponseEntity<UserDetails>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestParam(value = "username")String username)
    {
        try {
            userService.removeUser(username);
        }catch (Exception ex)
        {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<User>(HttpStatus.OK);
    }
}
