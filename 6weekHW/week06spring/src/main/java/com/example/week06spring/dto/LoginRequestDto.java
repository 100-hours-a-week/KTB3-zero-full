package com.example.week06spring.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginRequestDto {

    private String userEmail;
    private String userPassword;

    public LoginRequestDto() {};
    public LoginRequestDto(String userEmail, String userPassword) {
        this.userEmail=userEmail;
        this.userPassword=userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
