package com.suchit.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JayBhagvanController {

    @GetMapping("/")
    public String jayBhagvan() {
        return "Jay Bhagvan!";
    }

}
