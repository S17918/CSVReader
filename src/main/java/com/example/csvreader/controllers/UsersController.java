package com.example.csvreader.controllers;

import com.example.csvreader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    
}
