package com.willian.financial_organizer.controllers.interfaces;

import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import com.willian.financial_organizer.dtos.security.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Authentication for Authentication a User")
public interface IAuthController {
    @Operation(
            summary = "Authenticate an User",
            description = "This endpoint Authentication a user!",
            tags = {"Authentication"})
    ResponseEntity singIn(AccountCredentialsDTO data);

    @Operation(
            summary = "Refresh token",
            description = "This endpoint Refresh a token for a user!",
            tags = {"Authentication"})
    ResponseEntity refreshToken(String email, String refreshToken);
}
