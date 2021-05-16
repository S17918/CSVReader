package com.example.csvreader.models;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CsvBindByName(column = "first_name")
    private String firstName;

    @CsvBindByName(column = "last_name")
    private String lastName;

    @CsvBindByName(column = "birth_date")
    private String birthDate;

    private Date date;

    @CsvBindByName(column = "phone_no")
    private String phoneNumber;

    public User(String firstName, String lastName, String birthDate, Date date, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.date = date;
        this.phoneNumber = phoneNumber;
    }

    public User(String firstName, String lastName, String birthDate, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", date=" + date +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
