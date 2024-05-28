package com.willian.financial_organizer.services;

import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
import com.willian.financial_organizer.exceptions.ResourceNotFoundException;
import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.repositories.ReleasesRepository;
import com.willian.financial_organizer.services.interfaces.IReleasesServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReleasesServices  implements IReleasesServices {

    @Autowired
    private ReleasesRepository repository;

    @Override
    @Transactional
    public Releases save(Releases releases) {
        validateReleases(releases);

        releases.setStatus(ReleasesStatus.PENDENTE);

        return repository.save(releases);
    }

    @Override
    @Transactional
    public Releases update(Releases releases) {
        validateReleases(releases);

        Releases entity = repository.findById(releases.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return repository.save(releases);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Releases entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);
    }

    @Override
    public List<Releases> findAll() {
        return repository.findAll();
    }

    @Override
    public Releases findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    }

    @Override
    @Transactional
    public void updateStatus(Long id, ReleasesStatus status) {
        if(status == null) throw new RequiredObjectIsNullException("Status can't be null!");

        Releases entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setStatus(status);
        repository.save(entity);
    }

    @Override
    public void validateReleases(Releases releases) {

        if(releases == null) throw new RequiredObjectIsNullException();

        if(releases.getUserId() == null || releases.getUserId().getId() == null) {
            throw new RequiredObjectIsNullException("User ID is required!");
        }

        if(releases.getDescription() == null || releases.getDescription().trim().equals("")) {
            throw  new RequiredObjectIsNullException("Description is invalid.");
        }

        if(releases.getMonth() == null || releases.getMonth() < 1 || releases.getMonth() < 12) {
            throw new RequiredObjectIsNullException("Month is invalid.");
        }

        if(releases.getYear() == null || releases.getYear().toString().length() !=4) {
            throw new RequiredObjectIsNullException("Year is invalid.");
        }

        if (releases.getValue() == null || releases.getValue().compareTo(BigDecimal.ZERO) < 1) {
            throw new RequiredObjectIsNullException("Please insert a valid value.");
        }

        if(releases.getType() == null) {
            throw new RequiredObjectIsNullException("Please insert a type.");
        }
    }
}
