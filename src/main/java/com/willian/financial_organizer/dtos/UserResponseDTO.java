package com.willian.financial_organizer.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.willian.financial_organizer.model.User;

@JsonPropertyOrder({
        "id", "name", "email"
})
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
