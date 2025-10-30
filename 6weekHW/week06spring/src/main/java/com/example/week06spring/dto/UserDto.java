package com.example.week06spring.dto;

public class UserDto {
    private Long userId;
    private String userEmail;
    private String password;
    private String nickname;

    public UserDto() {}
    public UserDto(Long userId, String userEmail, String password, String nickname) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}

