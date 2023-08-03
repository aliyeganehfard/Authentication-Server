package com.oauth.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class DataController {

    @GetMapping("admin")
    public String admin(){
        return "hello admin";
    }

    @GetMapping("all")
    public String all(){
        return "hello all users";
    }
}
