package com.willian.financial_organizer.services;

import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.exceptions.RequiredObjectIsNullException;
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

}
