package com.ylizma.userservice.repositories;

import com.ylizma.userservice.entities.RoleApp;
import com.ylizma.userservice.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleAppRepository extends JpaRepository<RoleApp,Long> {
    RoleApp findByName(String name);
}
