package com.ylizma.userservice.services;

import com.ylizma.userservice.entities.RoleApp;
import com.ylizma.userservice.entities.UserApp;
import com.ylizma.userservice.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserAppRepository userAppRepository;

    public UserDetailsServiceImpl(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserApp> user = userAppRepository.findByUsername(userName);
        return user.map(value -> new User(value.getUsername(), value.getPassword(),getAuthority(value)))
                .orElseThrow(() -> new UsernameNotFoundException(userName + " Not found !!!"));
    }

    private Collection<? extends GrantedAuthority> getAuthority(UserApp user) {
        Set<GrantedAuthority> auth = new HashSet();
        for ( RoleApp role:user.getRoles()) {
            auth.add(new SimpleGrantedAuthority(role.getName()));
        }
        return auth;
    }

}
