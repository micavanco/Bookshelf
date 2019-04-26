package com.micavanco.bookshelf.service.implementations;

import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserService")
public class UserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if(user != null)
            return user;
        throw new UsernameNotFoundException("User "+username+" not found");
    }


    public List<User> getAll() {
        return userRepository.getAll();
    }


    public boolean removeUser(String username) {
        User user = userRepository.getByUsername(username);
        if(user == null)
            return false;
        userRepository.removeUser(user);

        return true;
    }


    public boolean addUser(User user) {
        try {
            userRepository.addUser(user);
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }


}
