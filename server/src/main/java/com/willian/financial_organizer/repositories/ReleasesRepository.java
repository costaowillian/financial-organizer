package com.willian.financial_organizer.repositories;

import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.model.enums.ReleasesTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {

    @Query("select sum(r.value) from Releases r join r.userId u where u.id = :userId and r.type = :type and r.status = :status group by u")
    BigDecimal getBalanceByTypeReleaseAndUserAndStatus(@Param("userId") Long userId, @Param("type") ReleasesTypes type,
                                                       @Param("status") ReleasesStatus status);
}
