package com.example.csvreader.controllers;

import com.example.csvreader.models.User;
import com.example.csvreader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/search/{lastName}")
    public List<User> getUsersByName(@PathVariable("lastName") String lastName){
        List<User> listByLastName = userRepository.findBySurname(lastName);
        return listByLastName;
    }

    @GetMapping(value = "/search")
    public List<User> getUsersByName(){
        List<User> listByLastName = userRepository.sortAscByDate();
        return listByLastName;
    }
}
