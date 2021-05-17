package com.example.csvreader.controllers;

import com.example.csvreader.models.User;
import com.example.csvreader.repositories.UserRepository;
import com.example.csvreader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users")
    public String users(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<User> userPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        int x = 0;

        for(User user : userRepository.findAll()){
            x++;
        }

        model.addAttribute("userPage", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("totalUsers", x);
        model.addAttribute("oldestUser", userRepository.findOldestUser());
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
