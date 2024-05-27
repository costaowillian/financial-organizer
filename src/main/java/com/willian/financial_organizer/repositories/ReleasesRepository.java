package com.willian.financial_organizer.repositories;

import com.willian.financial_organizer.model.Releases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {
}
