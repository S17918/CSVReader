package com.example.csvreader.controllers;

import com.example.csvreader.models.User;
import com.example.csvreader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/api")
    public List<User> api(){
        return userRepository.sortAscByDate();
    }
}
