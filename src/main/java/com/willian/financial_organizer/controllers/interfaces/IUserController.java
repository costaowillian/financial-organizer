package com.willian.financial_organizer.controllers.interfaces;

import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name="User", description = "endpoints for Managing Users")
public interface IUserController {

    @Operation(
            summary = "Create an User",
            description = "This endpoint create a new user!",
            tags = {"user"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = CreateUserDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<UserResponseDTO> create(CreateUserDTO userDTO);
}
