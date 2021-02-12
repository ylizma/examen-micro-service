package com.ylizma.userservice.services;

import com.ylizma.userservice.entities.RoleApp;
import com.ylizma.userservice.entities.UserApp;

import java.util.List;

public interface AccountService {

    UserApp addNewUser(UserApp user);

    RoleApp addNewRole(RoleApp role);

    void addRoleToUser(String username, String roleName);

    UserApp loadUserByUsername(String username);

    List<UserApp> allUsers();

    List<RoleApp> allRoles();
}
