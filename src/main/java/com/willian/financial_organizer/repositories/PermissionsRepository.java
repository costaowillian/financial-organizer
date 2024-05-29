package com.willian.financial_organizer.repositories;

import com.willian.financial_organizer.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permission, Long> {
}
