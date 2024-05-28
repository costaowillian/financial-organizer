package com.willian.financial_organizer.controllers;

import com.willian.financial_organizer.controllers.interfaces.IUserController;
import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.services.interfaces.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/user")
public class UserController implements IUserController {
    @Autowired
    private IUserServices services;

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> create(CreateUserDTO userDTO) {
        UserResponseDTO user = services.createUser(userDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }
}
