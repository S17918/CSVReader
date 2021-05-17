package com.example.csvreader.controllers;

import com.example.csvreader.models.User;
import com.example.csvreader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/users")
    public String users(Model model){
        int x = 0;

        for(User user : userRepository.findAll()){
            x++;
        }

        model.addAttribute("totalUsers", x);
        model.addAttribute("oldestUser", userRepository.findOldestUser());
        model.addAttribute("users", userRepository.sortAscByDate();
        return "users";
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));

        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());

        return "redirect:/users";
    }
}
