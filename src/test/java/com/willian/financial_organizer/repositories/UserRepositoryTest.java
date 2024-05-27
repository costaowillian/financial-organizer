package com.willian.financial_organizer.repositories;

import com.willian.financial_organizer.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @DisplayName("test Given An Existent Email When Exist By Email Should Return Boolean ")
    @Test
    void testGivenAnExistentEmail_WhenExistByEmail_ShouldReturnBoolean() {
    	//Given / Arrange
        User user  = new User();
        user.setEmail("willian@gmail.com");
        user.setName("Willian Costa");
        user.setPassword("password");

        repository.save(user);

    	//When / Act
        boolean exists = repository.existsByEmail("willian@gmail.com");

    	//Then /Assert
        assertTrue(exists, () -> "Should return true!");
    }

    @DisplayName("test Given A No Existent Email When Exist By Email Should Return Boolean ")
    @Test
    void testGivenANoExistentEmail_WhenExistByEmail_ShouldReturnBoolean() {
        //Given / Arrange

        //When / Act
        boolean exists = repository.existsByEmail("willian22@gmail.com");

        //Then /Assert
        assertFalse(exists, () -> "Should return false!");
    }
}
