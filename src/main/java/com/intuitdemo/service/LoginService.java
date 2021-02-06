package com.intuitdemo.service;


import java.util.HashSet;
import java.util.List;

import com.intuitdemo.model.MyUser;
import com.intuitdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUser user = userRepo.findByUsername(username.toLowerCase());
        HashSet<GrantedAuthority> roles = new HashSet<>();
        if (user == null)
            throw new UsernameNotFoundException("Username could not be found!");
        return new User(user.getUsername(), user.getPassword(), roles);
    }

    public void register(MyUser userinfo) {
        userinfo.setPassword(encoder.encode(userinfo.getPassword()));
        userinfo.setUsername(userinfo.getUsername().toLowerCase());
        userRepo.saveAndFlush(userinfo);
    }

    public boolean isExist(String username) {
        try {
            loadUserByUsername(username);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<MyUser> getAllUsers() {
        return userRepo.findAll();
    }

    public void remove(String username) {
        userRepo.deleteById(username);
    }

    public void removeAll() {
        userRepo.deleteAll();
    }

}