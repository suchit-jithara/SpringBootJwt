package com.suchit.jwt.controller;


import com.suchit.jwt.entity.UserPrincipal;
import com.suchit.jwt.entity.Users;
import com.suchit.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        System.out.println("================================================================");
        System.out.println("This is a login method of UserController class");
//        System.out.println(user);
//        return ResponseEntity.ok("Success");

        String s = this.userService.verifyUser(user);

        System.out.println("================================================================");
        System.out.println("This is a login 2 method of UserController class");

        return ResponseEntity.ok(s);
    }

    @PostMapping("/loginuser")
    public ResponseEntity<?> loginuser(@RequestBody Users user) {
        System.out.println(user);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/get")
    public UserDetails getCurrentUser () {
        return userService.getCurrentUserDetails();
    }
}
