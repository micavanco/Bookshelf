package com.micavanco.bookshelf.controller;

import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
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

}
