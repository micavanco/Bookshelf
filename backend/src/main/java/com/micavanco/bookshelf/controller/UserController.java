package com.micavanco.bookshelf.controller;

import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<User> getUser(@RequestParam(value = "username")String username,
                                        @RequestParam(value = "password")String password)
    {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            userRepository.addUser(user);
        }catch (Exception ex)
        {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return !username.isEmpty() ? new ResponseEntity<User>(user, HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestParam(value = "username")String username)
    {
        User user;
        try {
            user = userRepository.getByUsername(username);
        }catch (Exception ex)
        {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user != null ? new ResponseEntity<User>(user, HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestParam(value = "username")String username)
    {
        try {
            userRepository.deleteUserByUsername(username);
        }catch (Exception ex)
        {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<User>(HttpStatus.OK);
    }
}
