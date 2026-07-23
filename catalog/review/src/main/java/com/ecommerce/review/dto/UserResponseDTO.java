package com.ecommerce.review.dto;

public class UserResponseDTO {
    private Integer id;
    private String email;
    private String fullName;

    public UserResponseDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}