package com.ylizma.userservice.controllers;


import com.ylizma.userservice.entities.RoleApp;
import com.ylizma.userservice.entities.UserApp;
import com.ylizma.userservice.helper.UserRoleForm;
import com.ylizma.userservice.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> addNewUser(@RequestBody UserApp user) {
        UserApp newUser = accountService.addNewUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> addNewRole(@RequestBody RoleApp role) {
        RoleApp roleApp = accountService.addNewRole(role);
        return ResponseEntity.ok(roleApp);
    }

    @PostMapping("/users/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> addRoleToUser(@RequestBody UserRoleForm userRoleForm) {
        accountService.addRoleToUser(userRoleForm.getUsername(), userRoleForm.getRolename());
        return ResponseEntity.ok("added !!");
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(accountService.allUsers());
    }

    @GetMapping("/roles")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Object> getAllRoles() {
        System.out.println("/roles");
        return ResponseEntity.ok(accountService.allRoles());
    }
}
