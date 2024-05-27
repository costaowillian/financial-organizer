package com.willian.financial_organizer.services;

import com.willian.financial_organizer.exceptions.DuplicateResourceException;
import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.repositories.UserRepository;
import com.willian.financial_organizer.services.interfaces.IUserServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements IUserServices {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(User user) {

        if(user == null) throw new RequiredObjectIsNullException();

        emailIsValid(user.getEmail());

        user.setPassword(passwordEncoder(user.getPassword()));

        return repository.save(user);
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
        return passwordEncoder.encode(password);
    }
}
