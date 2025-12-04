package com.suchit.jwt.service;


import com.suchit.jwt.entity.Users;
import com.suchit.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public Users saveUser(Users user){
        return this.userRepository.save(user);
    }

    public String verifyUser(Users user) {
        System.out.println("================================================================");
        System.out.println("This is a verifyUser method of UserService class");
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        System.out.println("================================================================");
        System.out.println("This is a middle of verifyUser method of UserService class");

        if(authentication.isAuthenticated()) return jwtService.generateToken(user.getUsername());

        System.out.println("================================================================");
        System.out.println("This is a end of verifyUser method of UserService class");

        return "Failed";
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal())) {
            return null; // No authenticated user or anonymous user
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null; // No authenticated user or principal is not UserDetails
        }
        return (UserDetails) authentication.getPrincipal();
    }
}

