/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iv1201.server.service;

import com.iv1201.server.entity.User;
import com.iv1201.server.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author theok
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service @RequiredArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    private final UserRepository repository;
    
    public User loadUser(String username)throws UsernameNotFoundException{
       User user = repository.findByUsername(username);
       return user;
    }
    
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        
    }

    @Override
    public User getUser(String username) {
        return repository.findByUsername(username);
    }
    
    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }


    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }
    
    public User updateUser(User user){
        User existingUser = repository.findByEmail(user.getEmail());
        if(existingUser != null){
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
        }
        return repository.save(existingUser);
    }
}