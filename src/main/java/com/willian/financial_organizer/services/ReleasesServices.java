package com.willian.financial_organizer.services;

import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.repositories.ReleasesRepository;
import com.willian.financial_organizer.services.interfaces.IReleasesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleasesServices  implements IReleasesServices {

    @Autowired
    private ReleasesRepository repository;

    @Override
    public Releases save(Releases releases) {
        return null;
    }

    @Override
    public Releases update(Releases releases) {
        return null;
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
