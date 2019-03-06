package com.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019/3/1.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userName){
        String pass = "123456";
        List<String> roles = new ArrayList<>();
//        roles.add("admin2");
        roles.add("ADMIN");
        List<GrantedAuthority> auths = new ArrayList<>();
        for (String s :roles
             ) {
            GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(s);
            auths.add(simpleGrantedAuthority);
        }
        UserDetails user = new User(userName, pass, auths);
        return user;
    }
}
