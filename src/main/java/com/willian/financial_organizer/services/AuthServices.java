package com.willian.financial_organizer.services;

import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import com.willian.financial_organizer.dtos.security.TokenDTO;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.repositories.UserRepository;
import com.willian.financial_organizer.secutiry.jwt.JwtTokenProvider;
import com.willian.financial_organizer.services.interfaces.IAuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AuthServices implements IAuthServices {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Override
    public ResponseEntity signIn(AccountCredentialsDTO data) {
        try {
            String email = data.getEmail();
            String password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

            Optional<User> user = repository.findByEmail(email);

            TokenDTO tokenResponse;

            if(user.isPresent()) {
                tokenResponse = tokenProvider.createAccessToken(email);
            }else {
                throw new UsernameNotFoundException("Invalid email/password supplied");
            }

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

    @Override
    public ResponseEntity refreshToken(String email, String refreshToken) {
        Optional<User> user = repository.findByEmail(email);TokenDTO tokenResponse;

        if(user.isPresent()) {
            tokenResponse = tokenProvider.createAccessToken(email);
        }else {
            throw new UsernameNotFoundException("User not found! Please create your account!");
        }

        return ResponseEntity.ok(tokenResponse);
    }
}
