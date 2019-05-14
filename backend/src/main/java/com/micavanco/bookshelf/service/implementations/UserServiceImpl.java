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
        User user = userRepository.getByUsername(username);
        user.setRole(roleRepository.get(user.getId()).getRole());
        return user;
    }


    public List<User> getAll() {
        return userRepository.getAll();
    }


    public boolean removeUser(String username) {
        User user = userRepository.getByUsername(username);
        if(user == null)
            return false;
        userRepository.removeUser(user);
        Role role = roleRepository.get(user.getId());
        roleRepository.delete(role);
        return true;
    }


    public boolean addUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.addUser(user);
            User user_temp = userRepository.getByUsername(user.getUsername());
            Role role = new Role();
            role.setRole(user.getAuthorities().toString().substring(1).replace("]",""));
            role.setUser_id(user_temp.getId());
            roleRepository.add(role);
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public UserDetails loginUser(String username, String password)
    {
        UserDetails user_temp = loadUserByUsername(username);

        if(!user_temp.getPassword().equals(passwordEncoder.encode(password)))
            return null;

        return user_temp;
    }

    public User getUserById(Long id)
    {
        return userRepository.getById(id);
    }


}
