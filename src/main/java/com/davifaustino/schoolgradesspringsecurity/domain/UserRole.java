package com.davifaustino.schoolgradesspringsecurity.domain;

public enum UserRole {
    ADMIN("admin"),
    TEACHER("teacher"),
    STUDENT("student");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
