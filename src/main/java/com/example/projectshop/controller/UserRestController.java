package com.example.projectshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @GetMapping("/get")//localhost:8080/api/user/get
    public String getAccount(Principal principal){
        return "Welcome back : " + principal.getName();
    }

}
