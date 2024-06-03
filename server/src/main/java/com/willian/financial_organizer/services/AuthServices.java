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
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthServices implements IAuthServices {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Override
    public ResponseEntity signIn(AccountCredentialsDTO data) {
        try {
            String email = data.getEmail();
            String password = data.getPassword();

            validParams(email,password);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

            Optional<User> user = repository.findByEmail(email);

            TokenDTO tokenResponse;

            if(user.isPresent()) {
                tokenResponse = tokenProvider.createAccessToken(email, user.get().getRoles());
            }else {
                throw new UsernameNotFoundException("Invalid email/password supplied");
            }

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            logger.info("message" + e.getMessage());
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

    @Override
    public ResponseEntity refreshToken(String email, String refreshToken) {
        Optional<User> user = repository.findByEmail(email);

        TokenDTO tokenResponse;

        if(user.isPresent()) {
            tokenResponse = tokenProvider.createAccessToken(email, user.get().getRoles());
        }else {
            throw new UsernameNotFoundException("User not found! Please create your account!");
        }

        return ResponseEntity.ok(tokenResponse);
    }

    private void validParams(String email, String password) {
        if(email == null || email.isBlank() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email can't be null");
        }

        if(password ==null || password.isBlank() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password can't be null");
        }
    }
}
