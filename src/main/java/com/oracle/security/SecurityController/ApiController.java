package com.oracle.security.SecurityController;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/{id}")
    public String m1(){
        return "hello";
    }

    @GetMapping("/me")
    public String me(Authentication auth) {
        return "API user: " + auth.getName();
    }
    @GetMapping("/admin")
    public String admin() {
        return "API admin area";
    }
}
