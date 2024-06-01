package com.willian.financial_organizer.services.interfaces;

import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthServices {
    ResponseEntity signIn(AccountCredentialsDTO data);

    ResponseEntity refreshToken(String userName, String refreshToken);
}
