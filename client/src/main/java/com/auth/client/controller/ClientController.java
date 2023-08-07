package com.auth.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/")
public class ClientController {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("user")
    public String user(){
        return "user";
    }

    @GetMapping("allUsers")
    public String allUsers(){
        return "allUsers";
    }

}
