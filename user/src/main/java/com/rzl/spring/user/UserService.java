package com.rzl.spring.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String login() {
        return "Login successful";
    }

    public String logout() {
        return "Logout successful";
    }
}
