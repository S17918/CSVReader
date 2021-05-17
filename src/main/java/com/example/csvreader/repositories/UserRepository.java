package com.example.csvreader.repositories;

import com.example.csvreader.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM user ORDER BY DATE ASC", nativeQuery = true)
    List<User> sortAscByDate();

    @Query(value = "SELECT * FROM USER WHERE PHONE_NUMBER LIKE '_________' ORDER BY DATE ASC LIMIT 1", nativeQuery = true)
    List<User> findOldestUser();

    @Query(value = "SELECT * FROM user WHERE LOWER(LAST_NAME) LIKE %:NAME% ORDER BY DATE ASC", nativeQuery = true)
    List<User> findBySurname(@Param("NAME") String lastName);
}
