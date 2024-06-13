package com.willian.financial_organizer.controllers;

import com.willian.financial_organizer.controllers.interfaces.IReleasesController;
import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.services.ReleasesServices;
import com.willian.financial_organizer.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/releases")
public class ReleasesController implements IReleasesController {
    @Autowired
    private ReleasesServices service;

    private Logger logger = Logger.getLogger(UserServices.class.getName());

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleasesDTO> create(@RequestBody ReleasesDTO releasesDTO) {
        releasesDTO = service.save(releasesDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(releasesDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(releasesDTO);
    }

    @Override
    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleasesDTO> update(@RequestBody ReleasesDTO releasesDTO) throws Exception {
        return ResponseEntity.ok(service.update(releasesDTO));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<ReleasesDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "15") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "dec".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortDirection, "year"));
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleasesDTO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Override
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long id, @RequestBody ReleasesStatus status) {
        service.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
