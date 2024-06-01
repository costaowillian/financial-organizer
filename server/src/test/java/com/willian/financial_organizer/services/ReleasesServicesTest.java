package com.willian.financial_organizer.services;

import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
import com.willian.financial_organizer.exceptions.ResourceNotFoundException;
import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.model.enums.ReleasesTypes;
import com.willian.financial_organizer.repositories.ReleasesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReleasesServicesTest {

    @Mock
    private UserServices userService;

    @Mock
    private ReleasesRepository repository;

    @InjectMocks
    private ReleasesServices services;

    private Releases releases;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Willian Costa", "willian@gmail.com", "123@123");

        releases = new Releases(1L, "Lançamento qualquer", 5, 2024, user, BigDecimal.TEN, LocalDate.now(), ReleasesTypes.RECEITAS);
    }

    @DisplayName("test Create Given Release Object When Crete Should Return A Release Object")
    @Test
    void testCreate_GivenReleaseObject_WhenCrete_ShouldReturnAReleaseObject() {
        when(userService.findById(any(Long.class))).thenReturn(user);
        doReturn(releases).when(repository).save(any(Releases.class));

        // When / Act
        ReleasesDTO result = services.save(new ReleasesDTO(releases));

        // Then / Assert
        assertNotNull(result, () -> "Should Not Null");
        assertEquals(releases.getDescription(), result.getDescription(), () -> "Description should be the same");
    }

    @DisplayName("test Update Given Release Object When Update Release Should Return Release Object ")
    @Test
    void testUpdate_GivenReleaseObject_WhenUpdateRelease_ShouldReturnReleaseObject() {
    	//Given / Arrange;
        releases.setYear(2025);
        releases.setDescription("Venda de carro!");
        doReturn(releases).when(repository).save(any(Releases.class));

    	//When / Act
        ReleasesDTO result = services.save(new ReleasesDTO(releases));

    	//Then / Act
        assertNotNull(result, () -> "Should Not Null");
        assertEquals("Venda de carro!", result.getDescription(), () -> "Description should be Venda de carro!");
        assertEquals(2025, result.getYear(), ()-> "Year should be 2025");
    }

    @DisplayName("test Delete Given Release Id When Delete Release")
    @Test
    void testDelete_GivenReleaseId_WhenDeleteRelease() {
    	//Given / Arrange
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(releases));
        willDoNothing().given(repository).delete(releases);

    	//When / Act
        services.delete(1L);

    	//Then /Assert
        verify(repository, times(1)).delete(releases);
    }

    @DisplayName("test Find By Id Given Release Id When Find By Id Should Return Release Object")
    @Test
    void testFindById_GivenReleaseId_WhenFindById_ShouldReturnReleaseObject() {
    	//Given / Arrange
        doReturn(Optional.of(releases)).when(repository).findById(1L);

    	//When / Act
        ReleasesDTO result = services.findById(1L);

    	//Then /Assert
        assertNotNull(result, () -> "Should Not Null");
        assertEquals(releases.getDescription(), result.getDescription());
        assertEquals(releases.getId(), result.getId(), () -> "Ids should be the same!");
    }

    @DisplayName("test Find By Id Given Non Existent Release Id When Find By Id Should Throw Exception")
    @Test
    void testFindById_GivenNonExistentReleaseId_WhenFindById_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "No records found for this ID";

        //When / Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            services.findById(1L);
        }, () -> "Should be a exception");

        String actualMessage = exception.getMessage();

        //Then /Assert
        assertTrue(actualMessage.contains(expectedMessage), () ->"Should Contain the expected message: No records found for this ID");
    }

    @DisplayName("test Update Status Given Release Id When Update Release Status")
    @Test
    void testUpdateStatus_GivenReleaseId_WhenUpdateReleaseStatus() {
        //Given / Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(releases));
        when(repository.save(releases)).thenReturn(releases);

        //When / Act
        services.updateStatus(1L, ReleasesStatus.CANCELADO);

        //Then /Assert
        verify(repository, times(1)).save(releases);
    }

    @DisplayName("test Update Status Given Non Existent Release Id When Update Release Status Should Throw Exception")
    @Test
    void testUpdateStatus_GivenNonExistentReleaseId_WhenUpdateReleaseStatus_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "Status can't be null!";

        //When / Act
        IllegalArgumentException exception = assertThrows( IllegalArgumentException.class,
                () -> {
            services.updateStatus(releases.getId(), ReleasesStatus.valueOf(""));
            },
                () -> "Empty release Status should cause a Illegal Argument Exception"
        );

        //Then /Assert
        verify(repository, never()).save(any(Releases.class));
    }

    @DisplayName("test Validate Releases Given Null Release Object When Validate Releases Should throw exception")
    @Test
    void testValidateReleases_GivenNullReleaseObject_WhenValidateReleases_ShouldThrowException() {
    	//Given / Arrange
        String expectedMessage = "It is not allowed to persist a null object!";

    	//When / Act
        RequiredObjectIsNullException exception = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(null);
                },
                () -> "It is not allowed to persist a null object!"
        );

        String actualMessage = exception.getMessage();

    	//Then /Assert
        assertTrue(actualMessage.contains(expectedMessage), () -> "Should contain the expected message: It is not allowed to persist a null object!");
    }

    @DisplayName("test Type Is Valid Given Null Type When Validate Type Should Throw Exception")
    @Test
    void testValidateReleases_GivenNullNullType_WhenValidateType_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "Please insert a type.";
        releases.setType(null);

        //When / Act
        RequiredObjectIsNullException exception = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        String actualMessage = exception.getMessage();

        //Then /Assert
        assertTrue(actualMessage.contains(expectedMessage), () -> "Should contain the expected message: It is not allowed to persist a null object!");
    }

    @DisplayName("test Type Is Valid Given Invalid Value When Validate Value Should Throw Exception")
    @Test
    void testValidateReleases_GivenInvalidValue_WhenValidateValue_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "Please insert a valid value.";
        releases.setValue(null);

        //When / Act
        RequiredObjectIsNullException exception = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        String actualMessage = exception.getMessage();

        //Then /Assert
        assertTrue(actualMessage.contains(expectedMessage), () -> "Should contain the expected message: It is not allowed to persist a null object!");
    }

    @DisplayName("test Type Is Valid Given Invalid Year When Validate Year Should Throw Exception")
    @Test
    void testValidateReleases_GivenInvalidYear_WhenValidateYear_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "Year is invalid.";
        releases.setYear(20255);

        //When / Act
        RequiredObjectIsNullException exception = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        releases.setYear(null);

        //When / Act
        RequiredObjectIsNullException secondException = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        String actualMessage = exception.getMessage();
        String secondActualMessage = exception.getMessage();

        //Then /Assert
        assertTrue(actualMessage.contains(expectedMessage),
                () -> "Should contain the expected message: Please insert a valid value.!");
        assertTrue(secondActualMessage.contains(expectedMessage),
                () -> "Should contain the expected message: Please insert a valid value.!");
    }

    @DisplayName("test Type Is Valid Given Invalid Month When Validate Month Should Throw Exception")
    @Test
    void testValidateReleases_GivenInvalidMonth_WhenValidateMonth_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "Month is invalid.";
        releases.setMonth(0);

        //When / Act
        RequiredObjectIsNullException exception = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist a null object!"
        );

        releases.setMonth(null);

        //When / Act
        RequiredObjectIsNullException secondException = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist a null object!"
        );

        releases.setMonth(13);

        //When / Act
        RequiredObjectIsNullException thirdException = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist a null object!"
        );

        String actualMessage = exception.getMessage();
        String secondActualMessage = exception.getMessage();
        String thirdActualMessage = exception.getMessage();

        //Then /Assert
        assertTrue(actualMessage.contains(expectedMessage), () -> "Should contain the expected message: Month is invalid!");
        assertTrue(secondActualMessage.contains(expectedMessage), () -> "Should contain the expected message: Month is invalid!");
        assertTrue(thirdActualMessage.contains(expectedMessage), () -> "Should contain the expected message: Month is invalid");
    }

    @DisplayName("test Type Is Valid Given Invalid Description When Validate Description Should Throw Exception")
    @Test
    void testValidateReleases_GivenInvalidDescription_WhenValidateDescription_ShouldThrowException() {
        //Given / Arrange
        String expectedMessage = "Description is invalid.";
        releases.setDescription(null);

        //When / Act
        RequiredObjectIsNullException exception = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        releases.setDescription("            ");

        //When / Act
        RequiredObjectIsNullException secondException = assertThrows( RequiredObjectIsNullException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        String actualMessage = exception.getMessage();
        String secondActualMessage = exception.getMessage();

        //Then /Assert
        assertTrue(actualMessage.contains(expectedMessage),
                () -> "Should contain the expected message: Please insert a valid value.!");
        assertTrue(secondActualMessage.contains(expectedMessage),
                () -> "Should contain the expected message: Please insert a valid value.!");
    }

    @DisplayName("test Type Is Valid Given Invalid User ID When Validate User ID Id Should Throw Exception")
    @Test
    void testValidateReleases_GivenInvalidId_WhenValidateId_ShouldThrowException() {
        //Given / Arrange
        releases.setUserId(null);

        //When / Act
        NullPointerException exception = assertThrows( NullPointerException.class,
                () -> {
                    services.validateReleases(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        //Then /Assert
    }

    @DisplayName("test Update Given Not Allowed Field When Update Release Should Throw Exception")
    @Test
    void testUpdate_GivenNotAllowedField_WhenUpdateRelease_ShouldThrowException() {
    	//Given / Arrange
        releases.setYear(2025);
        releases.setDescription("Venda de carro!");
        releases.setRegistrationDate(LocalDate.now());

        //When / Act
        IllegalAccessException exception = assertThrows( IllegalAccessException.class,
                () -> {
                    services.update(new ReleasesDTO(releases));
                },
                () -> "It is not allowed to persist!"
        );

        //Then / Act
    }

}
