package com.willian.financial_organizer.services;

import com.willian.financial_organizer.exceptions.DuplicateResourceException;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.repositories.UserRepository;
import com.willian.financial_organizer.services.interfaces.IUserServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements IUserServices {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public User createUser(User user) {
        emailIsValid(user.getEmail());
        return repository.save(user);
    }

    @Override
    public void emailIsValid(String email) {
        boolean exists = repository.existsByEmail(email);

        if (exists) {
            throw new DuplicateResourceException("E-mail already registered!");
        }
    }
}
