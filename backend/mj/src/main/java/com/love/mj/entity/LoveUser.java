package com.love.mj.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;


@Entity
public class LoveUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank // Ensures username is not blank
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank // Ensures password is not blank
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Saves the enum as a string in the database
    @Column(nullable = false)
    private Role role;

    @Column(unique = true, nullable = true, length = 36) // Nullable for non-ADMIN roles
    private String uniqueCode;

    // Generate uniqueCode automatically before saving to the database
    @PrePersist
    public void generateUniqueCode() {
        if (this.role == Role.ADMIN) { // Only generate uniqueCode for ADMIN role
            this.uniqueCode = UUID.randomUUID().toString();
        } else {
            this.uniqueCode = null; // Non-ADMIN roles will not have uniqueCode
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    // Role Enum
    public enum Role {
        ADMIN,
        USER
    }
}

