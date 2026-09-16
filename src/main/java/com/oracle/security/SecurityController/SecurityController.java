package com.oracle.security.SecurityController;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @PreAuthorize(
            "#username == authentication.name or hasRole('ADMIN')"
    )
    @GetMapping("/user/{username}")
    public String userPage(@PathVariable String username){
        return "Profile of "+username;
    }
    @GetMapping("/status")
    public String status() {
        return "Running";
    }
    @GetMapping("/")
    public String home() {
        return "Public home";
    }
    @GetMapping("/profile")
    public String profile(Authentication auth) {
        return "Hello"+auth.getName();
    }
    @GetMapping("/admin/dashboard")
    public String admin() {
        return "Admin Dashboard";
    }
    @GetMapping("/reports/monthly")
    public String report() {
        return "monthly report";
    }
    @GetMapping("/reports/export")
    public String reportExport() {
        return "Report Export";
    }

}
