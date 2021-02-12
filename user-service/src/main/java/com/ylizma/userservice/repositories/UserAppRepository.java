package com.ylizma.userservice.repositories;

import com.ylizma.userservice.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findByUsername(String username);
}
