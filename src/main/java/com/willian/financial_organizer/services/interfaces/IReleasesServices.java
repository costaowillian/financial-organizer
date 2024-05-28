package com.willian.financial_organizer.services.interfaces;

import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesStatus;

import java.util.List;

public interface IReleasesServices {

    Releases save(Releases releases);

    Releases update(Releases releases);

    void delete(Long id);

    List<Releases> findAll();

    Releases findById(Long id);

    void updateStatus(Long id, ReleasesStatus status);

    void validateReleases(Releases releases);
}
