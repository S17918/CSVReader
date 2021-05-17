package com.example.csvreader.repositories;

import com.example.csvreader.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> sortAscByDate();

    @Query(value = "SELECT * FROM USER WHERE PHONE_NUMBER LIKE '_________' ORDER BY DATE ASC LIMIT 1", nativeQuery = true)
    List<User> findOldestUser();

}
