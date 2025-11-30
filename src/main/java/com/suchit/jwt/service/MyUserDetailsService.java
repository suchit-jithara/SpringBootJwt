package com.suchit.jwt.service;


import com.suchit.jwt.entity.UserPrincipal;
import com.suchit.jwt.entity.Users;
import com.suchit.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("================================================================");
        System.out.println("This is a loadUserByUsername method of MyUserDetailsService class.");
        System.out.println("MyUserDetailService loadUserByUsername executed : " + username);

        Users user = this.userRepository.findByUsername(username);
        if(user == null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
}
