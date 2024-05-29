package com.willian.financial_organizer.controllers;

import com.willian.financial_organizer.controllers.interfaces.IReleasesController;
import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.services.ReleasesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class ReleasesController implements IReleasesController {
    @Autowired
    private ReleasesServices service;

    @Override
    public ResponseEntity<ReleasesDTO> create(@RequestBody ReleasesDTO releasesDTO) {
        releasesDTO = service.save(releasesDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(releasesDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(releasesDTO);
    }

    @Override
    public ResponseEntity<ReleasesDTO> update(@RequestBody ReleasesDTO releasesDTO) throws Exception {
        return ResponseEntity.ok(service.update(releasesDTO));
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ReleasesDTO>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<ReleasesDTO> findById(Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable(value = "id") Long id, @RequestBody ReleasesStatus status) {
        service.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
