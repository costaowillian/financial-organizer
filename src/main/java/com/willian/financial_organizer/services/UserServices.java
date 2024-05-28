package com.willian.financial_organizer.services;

import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.exceptions.DuplicateResourceException;
import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.repositories.UserRepository;
import com.willian.financial_organizer.services.interfaces.IUserServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServices implements IUserServices, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public UserResponseDTO createUser(CreateUserDTO user) {

        if(user == null) throw new RequiredObjectIsNullException();

        emailIsValid(user.getEmail());

        user.setPassword(passwordEncoder(user.getPassword()));

        User savedUser = repository.save(createDtoToUser(user));

        return userToResponseDto(savedUser);
    }

    @Override
    public void emailIsValid(String email) {
        boolean exists = repository.existsByEmail(email);

        if (exists) {
            throw new DuplicateResourceException("E-mail already registered!");
        }
    }

    @Override
    public String passwordEncoder(String password) {
        Map<String, PasswordEncoder> encoderMap = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        encoderMap.put("pbkdf2", pbkdf2PasswordEncoder);

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoderMap);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);
        String result = passwordEncoder.encode(password);

        return result.substring("{pbkdf2}".length());
    }

    public User createDtoToUser(CreateUserDTO dto) {
        return new User(dto.getName(), dto.getName(), dto.getPassword());
    }

    public UserResponseDTO userToResponseDto(User user) {
        return new UserResponseDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        if(user.isPresent()) {
            return (UserDetails) user.get();
        } else {
            throw new UsernameNotFoundException("Username " + email + " not found!");
        }
    }
}
