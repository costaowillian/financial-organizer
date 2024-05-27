package com.willian.financial_organizer.services;

import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.repositories.UserRepository;
import com.willian.financial_organizer.services.interfaces.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements IUserServices {

    @Autowired
    private UserRepository repository;

    @Override
    public User auth(String email, String password) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public void emailIsValid(String email) {

    }
}
