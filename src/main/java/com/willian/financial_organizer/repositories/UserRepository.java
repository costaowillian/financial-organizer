package com.willian.financial_organizer.repositories;

import com.willian.financial_organizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
