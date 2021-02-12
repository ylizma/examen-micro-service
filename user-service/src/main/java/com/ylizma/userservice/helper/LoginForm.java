package com.ylizma.userservice.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginForm {
    private String username;
    private String password;
}
