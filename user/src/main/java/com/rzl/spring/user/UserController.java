package com.rzl.spring.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    public static final String USER_LOGIN = "user/login";
    public static final String USER_LOGOUT = "user/logout";

    @Autowired
    private UserService service;

    @GetMapping(path = USER_LOGIN)
    @LoadBalanced
    public String login() {
        return service.login();
    }

    @GetMapping(path = USER_LOGOUT)
    @LoadBalanced
    public String getUserLogout() {
        return service.logout();
    }
}
