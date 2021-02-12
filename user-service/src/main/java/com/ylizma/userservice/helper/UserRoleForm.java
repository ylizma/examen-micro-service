package com.ylizma.userservice.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleForm {

    private String username;
    private String rolename;

}
