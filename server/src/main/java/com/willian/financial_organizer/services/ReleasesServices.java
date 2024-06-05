package com.willian.financial_organizer.services;

import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
import com.willian.financial_organizer.exceptions.ResourceNotFoundException;
import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.model.enums.ReleasesTypes;
import com.willian.financial_organizer.repositories.ReleasesRepository;
import com.willian.financial_organizer.services.interfaces.IReleasesServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class ReleasesServices  implements IReleasesServices {

    @Autowired
    private ReleasesRepository repository;

    @Autowired
    private UserServices userService;

    @Autowired
    private PagedResourcesAssembler<ReleasesDTO> assembler;

    @Override
    @Transactional
    public ReleasesDTO save(ReleasesDTO releases) {
        validateReleases(releases);

        releases.setStatus(ReleasesStatus.PENDENTE);

        Releases release = dtoToRelease(releases);

        return releaseToDTO(repository.save(release));
    }

    @Override
    @Transactional
    public ReleasesDTO update(ReleasesDTO releases) throws Exception {
        validateUpdateRelease(releases);

        isAllowed(releases);

        Releases entity = findRelease(releases.getId());

        entity = updateRelease(entity, releases);

        return releaseToDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Releases entity = findRelease(id);

        repository.delete(entity);
    }

    @Override
    public PagedModel<EntityModel<ReleasesDTO>> findAll(Pageable pageable) {
        Page<Releases> releasesPage = repository.findAll(pageable);
        Page<ReleasesDTO> releasesDTOPage = releasesPage.map(x -> new ReleasesDTO(x));

        return assembler.toModel(releasesDTOPage);
    }

    @Override
    public ReleasesDTO findById(Long id) {
        Releases entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return releaseToDTO(entity);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, ReleasesStatus status) {
        if(status == null) throw new IllegalArgumentException("Status can't be null!");

        Releases entity = findRelease(id);

        entity.setStatus(status);
        repository.save(entity);
    }

    @Override
    public BigDecimal getBalanceByUser(Long id) {

        BigDecimal profit = BigDecimal.ZERO;
        BigDecimal expenses = BigDecimal.ZERO;

        BigDecimal profitResult = repository.getBalanceByTypeReleaseAndUser(id, ReleasesTypes.RECEITAS);
        BigDecimal expensesResult = repository.getBalanceByTypeReleaseAndUser(id, ReleasesTypes.DESPESAS);

        if (profitResult != null) {
            profit = profitResult;
        }

        if (expensesResult != null) {
            expenses = expensesResult;
        }

        return profit.subtract(expenses);
    }

    @Override
    public void validateReleases(ReleasesDTO releases) {

        if(releases == null) throw new RequiredObjectIsNullException();

        idIsValid(releases);

        descriptionIsValid(releases);

        monthIsValid(releases);

        yearISValid(releases);

        valueIsValid(releases);

        typeIsValid(releases);
    }

    private static void typeIsValid(ReleasesDTO releases) {
        if(releases.getType() == null) {
            throw new RequiredObjectIsNullException("Please insert a type.");
        }
    }

    private static void valueIsValid(ReleasesDTO releases) {
        if (releases.getValue() == null || releases.getValue().compareTo(BigDecimal.ZERO) < 1) {
            throw new RequiredObjectIsNullException("Please insert a valid value.");
        }
    }

    private static void yearISValid(ReleasesDTO releases) {
        if(releases.getYear() == null || releases.getYear().toString().length() !=4) {
            throw new RequiredObjectIsNullException("Year is invalid.");
        }
    }

    private static void monthIsValid(ReleasesDTO releases) {
        if(releases.getMonth() == null || releases.getMonth() < 1 || releases.getMonth() > 12) {
            throw new RequiredObjectIsNullException("Month is invalid.");
        }
    }

    private static void descriptionIsValid(ReleasesDTO releases) {
        if(releases.getDescription() == null || releases.getDescription().trim().equals("")) {
            throw  new RequiredObjectIsNullException("Description is invalid.");
        }
    }

    private static void idIsValid(ReleasesDTO releases) {
        if(releases.getUserId() == null) {
            throw new RequiredObjectIsNullException("User ID is required!");
        }
    }

    private Releases findRelease(Long id) {
        Releases entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return  entity;
    }

    private void validateUpdateRelease(ReleasesDTO releasesDTO) {
        if(releasesDTO == null) throw new RequiredObjectIsNullException();

        descriptionIsValid(releasesDTO);

        monthIsValid(releasesDTO);

        yearISValid(releasesDTO);

        valueIsValid(releasesDTO);

        typeIsValid(releasesDTO);
    }

    private Releases updateRelease(Releases entity, ReleasesDTO releasesDTO) {
        entity.setDescription(releasesDTO.getDescription());
        entity.setMonth(releasesDTO.getMonth());
        entity.setValue(releasesDTO.getValue());
        entity.setYear(releasesDTO.getYear());
        entity.setType(releasesDTO.getType());
        return entity;
    }

    private void isAllowed(ReleasesDTO releasesDTO) throws Exception {
        Set<String> validFields = new HashSet<>();
        validFields.add("description");
        validFields.add("month");
        validFields.add("value");
        validFields.add("year");
        validFields.add("type");

        for (Field field : releasesDTO.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(releasesDTO) != null && !validFields.contains(field.getName())) {
                throw new IllegalAccessException("This field is not allowed: " + field.getName());
            }
        }
    }

    private Releases dtoToRelease(ReleasesDTO releasesDTO) {
        Releases releases = new Releases();
        releases.setStatus(releasesDTO.getStatus());
        releases.setDescription(releasesDTO.getDescription());
        releases.setMonth(releasesDTO.getMonth());
        releases.setValue(releasesDTO.getValue());
        releases.setYear(releasesDTO.getYear());
        releases.setRegistrationDate(releasesDTO.getRegistrationDate());
        releases.setUserId(userService.findById(releasesDTO.getUserId()));
        releases.setType(releasesDTO.getType());
        return releases;
    }

    private ReleasesDTO releaseToDTO(Releases releases) {
        return  new ReleasesDTO(releases);
    }
}
