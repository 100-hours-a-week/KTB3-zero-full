package com.example.week04_hw.dto;

public class UserDto {
    private Long id;
    private String userId;
    private String username;
    private String passwordHash;
    private String displayName;

    public UserDto() {}

    public UserDto(Long id, String username, String passwordHash, String displayName) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
    }

    public UserDto(Long id, String userId, String username, String passwordHash, String displayName) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.displayName = displayName;
    }

    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getDisplayName() { return displayName; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
