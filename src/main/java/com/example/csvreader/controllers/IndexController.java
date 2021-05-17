package com.example.csvreader.controllers;

import com.example.csvreader.models.User;
import com.example.csvreader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/")
    public String index(Model model){
        int x = 0;

        for(User user : userRepository.findAll()){
            x++;
        }

        model.addAttribute("totalUsers", x);
        model.addAttribute("oldestUser", userRepository.findOldestUser());
        return "index";
    }



}
