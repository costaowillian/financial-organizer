package com.willian.financial_organizer.controllers;

import com.willian.financial_organizer.controllers.interfaces.IAuthController;
import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import com.willian.financial_organizer.services.interfaces.IAuthServices;
import com.willian.financial_organizer.services.interfaces.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController implements IAuthController {
    @Autowired
    private IAuthServices authServices;

    @Autowired
    private IUserServices services;

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> create(@RequestBody CreateUserDTO userDTO) {
        UserResponseDTO user = services.createUser(userDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @Override
    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity singIn(@RequestBody AccountCredentialsDTO data) {
        if(checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

        ResponseEntity token = authServices.signIn(data);

        if(token == null) {
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");
        }
        return token;
    }

    @Override
    @PutMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity refreshToken(@RequestHeader("email") String email, @RequestHeader("refreshToken")String refreshToken) {

        if(checkIfParamsIsNotNull(email, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request!");

        var token = authServices.refreshToken(email,refreshToken);

        if(token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request!");
        }
        return token;
    }

    private static boolean checkIfParamsIsNotNull(String userName, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() || userName == null || userName.isBlank();
    }

    private boolean checkIfParamsIsNotNull(AccountCredentialsDTO data) {
        return data == null || data.getEmail() == null || data.getEmail().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
