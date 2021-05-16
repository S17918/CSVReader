package com.example.csvreader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(path = "/")
    public String index(Model model){
        return "index";
    }

    @RequestMapping(path = "/add")
    public String add(Model model){
        return "add";
    }

    @RequestMapping(path = "/users")
    public String users(Model model){
        return "users";
    }

}
