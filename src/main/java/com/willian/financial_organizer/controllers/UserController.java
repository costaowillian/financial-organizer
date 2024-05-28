package com.willian.financial_organizer.controllers;

import com.willian.financial_organizer.controllers.interfaces.IUserController;
import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


public class UserController implements IUserController {
    @Autowired
    private UserServices services;

    @Override
    public ResponseEntity<UserResponseDTO> create(CreateUserDTO userDTO) {
        UserResponseDTO user = services.createUser(userDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }
}
