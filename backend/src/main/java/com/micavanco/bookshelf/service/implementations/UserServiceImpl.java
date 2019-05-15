package com.micavanco.bookshelf.service.implementations;

import com.micavanco.bookshelf.model.Role;
import com.micavanco.bookshelf.model.User;
import com.micavanco.bookshelf.repository.RoleRepository;
import com.micavanco.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserService")
public class UserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByUsername(username);
    }


    public List<User> getAll() {
        return userRepository.getAll();
    }


    public boolean removeUser(String username, String password) {
        User user = userRepository.getByUsername(username);
        if(user == null || !passwordEncoder.matches(password, user.getPassword()))
            return false;
        userRepository.removeUser(user);
        return true;
    }


    public boolean addUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.addUser(user);
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public UserDetails loginUser(String username, String password)
    {
        UserDetails user_temp = loadUserByUsername(username);

        if(!passwordEncoder.matches(password, user_temp.getPassword()))
            return null;

        return user_temp;
    }

    public User getUserById(Long id)
    {
        return userRepository.getById(id);
    }


}
