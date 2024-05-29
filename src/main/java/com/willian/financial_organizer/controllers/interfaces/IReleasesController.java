package com.willian.financial_organizer.controllers.interfaces;

import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name="Releases", description = "Endpoints for Managing Releases")
public interface IReleasesController {

    @Operation(
            summary = "Create Release",
            description = "This endpoint create a new Release!",
            tags = {"Releases"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = ReleasesDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<ReleasesDTO> create(ReleasesDTO releasesDTO);

    @Operation(
            summary = "Update Release",
            description = "This endpoint update a new Release!",
            tags = {"Releases"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = ReleasesDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<ReleasesDTO> update(ReleasesDTO releasesDTO);

    @Operation(
            summary = "Delete Release",
            description = "This endpoint Delete a Release!",
            tags = {"Releases"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<?> delete(Long id);

    @Operation(
            summary = "Find All Release",
            description = "This endpoint FindAll Release!",
            tags = {"Releases"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = ReleasesDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<List<ReleasesDTO>> findAll();

    @Operation(
            summary = "Find a Release by id",
            description = "This endpoint find a Release by id!",
            tags = {"Releases"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = ReleasesDTO.class)
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<ReleasesDTO> findById(Long id);

    @Operation(
            summary = "Delete Release",
            description = "This endpoint Delete a Release!",
            tags = {"Releases"},
            responses = {
                    @ApiResponse(description = "No Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    ResponseEntity<?> updateStatus(Long id, ReleasesStatus status);

}
