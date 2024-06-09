package com.willian.financial_organizer.services;

import com.willian.financial_organizer.configs.SecurityConfig;
import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.exceptions.DuplicateResourceException;
import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
import com.willian.financial_organizer.model.Permission;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.repositories.PermissionsRepository;
import com.willian.financial_organizer.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServicesTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServices service;

    private User user;

    @BeforeAll
    void setUp() {
        user = new User("Willian Costa", "willian@gmail.com", "senha123");
    }

    @DisplayName("test Given User When Create Should Return A User Object")
    @Test
    void testGivenUser_WhenCrete_ShouldReturnAUserObject() {
    	//Given / Arrange
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any(User.class))).thenReturn(user);

    	//When / Act
        UserResponseDTO savedUser = service.createUser(new CreateUserDTO(user));

    	//Then /Assert
        assertNotNull(savedUser, () -> "Should not return null");
        assertEquals(user.getName(), savedUser.getName(), () -> "Should return " + user.getName() + " but return " + savedUser.getName() + "!");
        assertEquals(user.getEmail(), savedUser.getEmail(), () -> "Should return " + user.getEmail() + " but return " + savedUser.getEmail() + "!");
    }

    @DisplayName("test Given A Empty User When Create Should Throw Exception")
    @Test
    void testGivenEmptyUser_WhenCrete_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "It is not allowed to persist a null object!";

        //When / Act
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.createUser(null);
        });

        String actualMessage = exception.getMessage();

        //Then /Assert
        assertTrue(actualMessage.contains(expectedMessage), () -> "Should contains the message " + expectedMessage + "!");
    }

    @DisplayName("test Given A No Existent Email When Email Is Valid")
    @Test
    void testGivenANoExistentEmail_WhenEmailIsValid() {
    	//Given / Arrange
        when(repository.existsByEmail(anyString())).thenReturn(false);

    	//When / Act
        service.emailIsValid(user.getEmail());

    	//Then /Assert
        verify(repository, times(1)).existsByEmail(user.getEmail());
    }

    @DisplayName("test Given A No Existent Email When Email Is Valid Should Throw Exception")
    @Test
    void testGivenAnExistentEmail_WhenEmailIsValid_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "E-mail already registered!";
        when(repository.existsByEmail(anyString())).thenReturn(true);

        //When / Act
        Exception exception = assertThrows(DuplicateResourceException.class, () -> {
            service.emailIsValid("willian22@gmail.com");
        });

        //Then /Assert
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("test Given Any String Password Encoder Should Return a Password Encoder")
    @Test
    void testGivenAnyString_WhenPasswordEncoder_ShouldReturnPasswordEncoded() {
        //Given / Arrange
        String password = "teste123";

        //When / Act
        String result = service.passwordEncoder(password);

        //Then /Assert
        assertNotEquals(password, result, () -> "Should not be the same!");
        assertNotNull(result, () -> "Should not null!");
    }

}
