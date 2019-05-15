package com.micavanco.bookshelf.controller;

import com.micavanco.bookshelf.configuration.JwtTokenProvider;
import com.micavanco.bookshelf.model.Role;
import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.payload.JWTLoginSuccessResponse;
import com.micavanco.bookshelf.payload.LoginRequest;
import com.micavanco.bookshelf.repository.UserRepository;
import com.micavanco.bookshelf.service.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

import static com.micavanco.bookshelf.configuration.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserServiceImpl userService;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserServiceImpl userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager)
    {
        this.userService = userService;
        this.tokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestParam(value = "username")String username,
                                        @RequestParam(value = "password")String password,
                                        @RequestParam(value = "confirmPassword")String confirmPassword)
    {
        UserDetails user_temp = userService.loadUserByUsername(username);
        if(user_temp != null)
        {
            LinkedMultiValueMap<String, String>  linkedMultiValueMap = new LinkedMultiValueMap();
            linkedMultiValueMap.add("error", "This username already exist");
            return new ResponseEntity<User>(linkedMultiValueMap,HttpStatus.CONFLICT);
        }

        if(!password.equals(confirmPassword))
        {
            LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap();
            linkedMultiValueMap.add("error", "Confirm password is not the same as password");
            return new ResponseEntity<User>(linkedMultiValueMap,HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        Role role = new Role();
        role.setRole("ROLE_USER");
        user.addRole(role);
        try {
            userService.addUser(user);
        }catch (Exception ex)
        {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return !username.isEmpty() ? new ResponseEntity<User>(user, HttpStatus.OK)
                : new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
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


    @CrossOrigin(origins = "http://localhost:5000")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@RequestParam(value = "username")String username,
                                           @RequestParam(value = "password")String password)
    {
        try {
            userService.removeUser(username, password);
        }catch (Exception ex)
        {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:5000")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        UserDetails user;
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));

        /*
        try {
            user = userService.loginUser(username, password);
        }catch (Exception ex)
        {
            return new ResponseEntity<UserDetails>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user != null ? new ResponseEntity<UserDetails>(user, HttpStatus.OK)
                : new ResponseEntity<UserDetails>(HttpStatus.NO_CONTENT);*/
    }
}
