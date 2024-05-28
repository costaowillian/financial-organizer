package com.willian.financial_organizer.services;

import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
import com.willian.financial_organizer.exceptions.ResourceNotFoundException;
import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.repositories.ReleasesRepository;
import com.willian.financial_organizer.services.interfaces.IReleasesServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReleasesServices  implements IReleasesServices {

    @Autowired
    private ReleasesRepository repository;

    @Override
    @Transactional
    public Releases save(Releases releases) {
        return repository.save(releases);
    }

    @Override
    @Transactional
    public Releases update(Releases releases) {
        if(releases == null) throw new RequiredObjectIsNullException();

        Releases entity = repository.findById(releases.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return repository.save(releases);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<Releases> findAll() {
        return List.of();
    }

    @Override
    public Releases findById(String id) {
        return null;
    }

    @Override
    public void updateStatus(String id, ReleasesStatus status) {

    }
}
