package com.ylizma.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@AllArgsConstructor
@Data
@ToString
@NoArgsConstructor
public class RoleApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    private Collection<UserApp> users;
}
