package com.willian.financial_organizer.repositories;

import com.willian.financial_organizer.integrationsTest.testContainers.AbstractIntegrationTest;
import com.willian.financial_organizer.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository repository;

    @DisplayName("test Given An Existent Email When Exist By Email Should Return Boolean")
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

    @DisplayName("test Given A No Existent Email When Exist By Email Should Return Boolean")
    @Test
    void testGivenANoExistentEmail_WhenExistByEmail_ShouldReturnBoolean() {
        //Given / Arrange

        //When / Act
        boolean exists = repository.existsByEmail("willian22@gmail.com");

        //Then /Assert
        assertFalse(exists, () -> "Should return false!");
    }

    @DisplayName("test Given An Existent Email When Find By Email Should Return User Object")
    @Test
    void testGivenAnExistentEmail_WhenFindByEmail_ShouldReturnUserObject() {
        //Given / Arrange
        User user  = new User();
        user.setEmail("willian@gmail.com");
        user.setName("Willian Costa");
        user.setPassword("password");

        //When / Act
        User result = repository.findByEmail(user.getEmail()).get();

        //Then /Assert
        assertNotNull(result, () -> "Should not null");
        assertEquals(user.getEmail(), result.getEmail(), () -> "Should return "+ user.getEmail() + " but return " + result.getEmail() + "!");
    }

    @DisplayName("test Given An Existent Email When Find By Email Should Return Null")
    @Test
    void testGivenAnExistentEmail_WhenFindByEmail_ShouldReturnNull() {
        //Given / Arrange

        //When / Act
        Optional<User> result = repository.findByEmail("willia22@gmail.vom");

        //Then /Assert
        assertFalse(result.isPresent(), () -> "Should return null");
    }
}
