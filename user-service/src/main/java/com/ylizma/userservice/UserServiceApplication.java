package com.ylizma.userservice;

import com.ylizma.userservice.entities.RoleApp;
import com.ylizma.userservice.entities.UserApp;
import com.ylizma.userservice.repositories.RoleAppRepository;
import com.ylizma.userservice.repositories.UserAppRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserAppRepository userAppRepository, RoleAppRepository roleAppRepository, PasswordEncoder passwordEncoder){
        return args -> {
            RoleApp admin = new RoleApp();
            admin.setName("ADMIN");
            roleAppRepository.save(admin);
            RoleApp user = new RoleApp();
            user.setName("USER");
            roleAppRepository.save(user);
            Set<RoleApp> roles = new HashSet<>();
            roles.add(user);
            roles.add(admin);
            UserApp user1 = new UserApp(null,"admin",passwordEncoder.encode("admin"),roles);
            roles.remove(admin);
            UserApp user2 = new UserApp(null,"user",passwordEncoder.encode("user"),roles);
            userAppRepository.save(user1);
            userAppRepository.save(user2);
        };
    }
}
