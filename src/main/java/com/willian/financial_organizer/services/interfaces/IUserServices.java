package com.willian.financial_organizer.services.interfaces;

import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.model.User;

public interface IUserServices {

    UserResponseDTO createUser(CreateUserDTO user);

    void emailIsValid(String email);

    String passwordEncoder(String password);
}
