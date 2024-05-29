package com.willian.financial_organizer.repositories;

import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.enums.ReleasesTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {

    @Query(value = "select sum(l.value) form Releases r join r.userId u where u.id =: userId l.type =:type" +
            "group by u")
    BigDecimal getBalanceByTypeReleaseAndUser(@Param("userId") Long userId,@Param("type")  ReleasesTypes type);
}
