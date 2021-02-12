package com.ylizma.userservice.services;

import com.ylizma.userservice.entities.RoleApp;
import com.ylizma.userservice.entities.UserApp;
import com.ylizma.userservice.repositories.RoleAppRepository;
import com.ylizma.userservice.repositories.UserAppRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    private final UserAppRepository userAppRepository;
    private final RoleAppRepository roleAppRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UserAppRepository userAppRepository, RoleAppRepository roleAppRepository, PasswordEncoder passwordEncoder) {
        this.userAppRepository = userAppRepository;
        this.roleAppRepository = roleAppRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserApp addNewUser(UserApp user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userAppRepository.save(user);
    }

    @Override
    public RoleApp addNewRole(RoleApp role) {
        return roleAppRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        RoleApp role = roleAppRepository.findByName(roleName);
        UserApp user = userAppRepository.findByUsername(username).get();
        user.getRoles().add(role);
        userAppRepository.save(user);
    }

    @Override
    public UserApp loadUserByUsername(String username) {
        return userAppRepository.findByUsername(username).get();
    }

    @Override
    public List<UserApp> allUsers() {
        return userAppRepository.findAll();
    }

    @Override
    public List<RoleApp> allRoles() {
        return roleAppRepository.findAll();
    }
}
