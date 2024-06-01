package com.willian.financial_organizer.services;

import com.willian.financial_organizer.dtos.CreateUserDTO;
import com.willian.financial_organizer.dtos.ReleasesDTO;
import com.willian.financial_organizer.dtos.UserResponseDTO;
import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.User;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

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

        when(userService.findById(any(Long.class))).thenReturn(user);
        when(userService.createUser(any(CreateUserDTO.class))).thenReturn(new UserResponseDTO(user));
        userService.createUser(new CreateUserDTO(user));

        releases = new Releases(1L, "Lançamento qualquer", 5, 2024, user, BigDecimal.TEN, LocalDate.now(), ReleasesTypes.RECEITAS);
    }

    @DisplayName("test Create Given Release Object When Crete Should Return A Release Object")
    @Test
    void testCreate_GivenReleaseObject_WhenCrete_ShouldReturnAReleaseObject() {
        doReturn(releases).when(repository).save(any(Releases.class));

        // When / Act
        ReleasesDTO result = services.save(new ReleasesDTO(releases));

        // Then / Assert
        assertNotNull(result, () -> "Should Not Null");
        assertEquals(releases.getDescription(), result.getDescription());
    }
}
