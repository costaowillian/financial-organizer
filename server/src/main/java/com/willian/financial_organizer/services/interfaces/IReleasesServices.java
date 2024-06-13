package com.willian.financial_organizer.services.interfaces;

import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.model.enums.ReleasesTypes;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.util.List;

public interface IReleasesServices {

    ReleasesDTO save(ReleasesDTO releases);

    ReleasesDTO update(ReleasesDTO releases) throws Exception;

    void delete(Long id);

    PagedModel<EntityModel<ReleasesDTO>> findAll(Pageable pageable);

    ReleasesDTO findById(Long id);

    void updateStatus(Long id, ReleasesStatus status);

    void validateReleases(ReleasesDTO releases);

    BigDecimal getBalanceByUser(Long id);
}
