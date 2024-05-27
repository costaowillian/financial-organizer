package com.willian.financial_organizer.services;

import com.willian.financial_organizer.exceptions.DuplicateResourceException;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
