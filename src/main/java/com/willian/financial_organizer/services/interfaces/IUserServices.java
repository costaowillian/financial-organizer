package com.willian.financial_organizer.services.interfaces;

import com.willian.financial_organizer.model.User;

public interface IUserServices {

    User auth(String email, String password);

    User createUser(User user);

    void emailIsValid(String email);
}
