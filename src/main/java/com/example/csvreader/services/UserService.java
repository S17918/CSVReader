package com.example.csvreader.services;

import com.example.csvreader.models.User;
import com.example.csvreader.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> findPaginated(Pageable pageable){
        List<User> users = userRepository.sortAscByDate();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> userList;

        if(users.size() < startItem){
            userList = Collections.emptyList();
        }else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            userList = users.subList(startItem, toIndex);
        }

        Page<User> userPage = new PageImpl<User>(userList, PageRequest.of(currentPage, pageSize), users.size());
        return userPage;
    }
}
