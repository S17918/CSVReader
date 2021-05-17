package com.example.csvreader.controllers;

import com.example.csvreader.models.User;
import com.example.csvreader.repositories.UserRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class AddController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/add")
    public String add(Model model){ return "add"; }

    @RequestMapping(path = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if(file.isEmpty()){
            model.addAttribute("status", false);
            model.addAttribute("warningStatus", false);
            model.addAttribute("errorMessage", "Select CSV file");
        }else{
            try{
                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "Windows-1250"));
                CsvToBean<User> csv = new CsvToBeanBuilder(reader)
                        .withType(User.class)
                        .withIgnoreEmptyLine(true)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(';')
                        .build();

                List<User> userList = csv.parse();
                List<User> tempList = new ArrayList<>();
                List<User> skippedList = new ArrayList<>();
                List<User> finalList = new ArrayList<>();

                int x = 0;
                for(User user:userRepository.findAll()){
                    x++;
                }

                for (User user : userList) {
                    if(user.getFirstName().length() != 0 && user.getLastName().length() != 0 && user.getBirthDate().length() != 0){
                        user.setDate(parseDateToSQL(user.getBirthDate()));
                        if(user.getPhoneNumber().length() == 9) {
                            if(!checkPhoneNumber(user, tempList)){
                                tempList.add(user);
                            }else {
                                skippedList.add(user);
                            }
                        }else{
                            if(!user.getPhoneNumber().equals("")){
                                user.setPhoneNumber("");
                            }

                            if(x == 0){
                                tempList.add(user);
                            }else{
                                for(User user2 : userRepository.findAll()){
                                    if(user2.getFirstName().equals(user.getFirstName()) && user2.getLastName().equals(user.getLastName()) && user2.getDate().equals(user.getDate())) {
                                        skippedList.add(user);
                                        break;
                                    }else{
                                        tempList.add(user);
                                        break;
                                    }
                                }
                            }
                        }
                    }else{
                        skippedList.add(user);
                    }
                }

                if(!tempList.isEmpty()){
                    for(User user : tempList) {
                        if(x == 0){
                            finalList.add(user);
                        }else {
                            boolean check = false;
                            for(User user2 : userRepository.findAll()){
                                if(Objects.equals(user2.getPhoneNumber(), user.getPhoneNumber())){
                                    if(user2.getFirstName().equals(user.getFirstName()) && user2.getLastName().equals(user.getLastName()) && user2.getDate().equals(user.getDate())) {
                                        skippedList.add(user);
                                        check = true;
                                    }else{
                                        if(user.getPhoneNumber().length() == 9 || user2.getPhoneNumber().length() == 9){
                                            skippedList.add(user);
                                            check = true;
                                        }
                                    }
                                }
                            }
                            if(!check){
                                finalList.add(user);
                            }
                        }
                    }
                }else {
                    model.addAttribute("status", false);
                    model.addAttribute("errorMessage", "No users added! Perhaps they already exists in database?");
                }

                for(User user : finalList){
                    System.out.println("Added to database -> " + user.getFirstName() + ' ' + user.getLastName() + ' ' + user.getDate() + ' ' + user.getPhoneNumber());
                    userRepository.save(user);
                }

                if(!skippedList.isEmpty()){
                    model.addAttribute("warningStatus", true);
                    model.addAttribute("warningMessage", "Some users has been skipped! Perhaps first name, last name or birth day were null value or phone number already exists in database?");
                }else{
                    model.addAttribute("warningStatus", false);
                    model.addAttribute("successMessage", "All data has been uploaded");
                }

                model.addAttribute("users", finalList);
                model.addAttribute("skippedUsers", skippedList);
                model.addAttribute("status", true);
                model.addAttribute("successMessage", "All data has been uploaded");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Error occured during file processing - " + e.getMessage());
                model.addAttribute("status", false);
            }
        }
        return "status";
    }

    private java.sql.Date parseDateToSQL(String string) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = format.parse(string);
        return new java.sql.Date(date.getTime());
    }

    private boolean checkPhoneNumber(User user, List<User> finalList){
        boolean check = false;
        for(User user2 : finalList){
            if(Objects.equals(user2.getPhoneNumber(), user.getPhoneNumber())){
                check = true;
                break;
            }else{
                for(User user3 : userRepository.findAll()){
                    if (Objects.equals(user3.getPhoneNumber(), user.getPhoneNumber())){
                        check = true;
                        break;
                    }
                }
            }
        }
        return check;
    }
}
