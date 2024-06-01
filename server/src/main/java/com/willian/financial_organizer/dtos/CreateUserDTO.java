package com.willian.financial_organizer.dtos;

import com.willian.financial_organizer.model.Permission;
import com.willian.financial_organizer.model.User;

import java.util.List;

public class CreateUserDTO {

    private String name;

    private String email;

    private String password;

    private List<Long> permissions;

    public CreateUserDTO() {
    }

    public CreateUserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Long> permissions) {
        this.permissions = permissions;
    }
}
