package com.auth.identity.domain;


import lombok.Data;

@Data
public class RoleToUser
{
    private String user;
    private String role;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

