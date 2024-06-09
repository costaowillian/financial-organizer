package com.willian.financial_organizer.controllers;

import com.willian.financial_organizer.configs.TestConfigs;
import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import com.willian.financial_organizer.dtos.security.TokenDTO;
import com.willian.financial_organizer.integrationsTest.dtos.ReleasesDTO;
import com.willian.financial_organizer.integrationsTest.testContainers.AbstractIntegrationTest;
import com.willian.financial_organizer.integrationsTest.wrappers.WrapperReleaseDTO;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.model.enums.ReleasesTypes;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ReleaseControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static ReleasesDTO releasesDTO;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        releasesDTO = new ReleasesDTO();
    }

    @Test
    @Order(0)
    @DisplayName("Test Api authorization")
    public void authorization() {
        AccountCredentialsDTO user = new AccountCredentialsDTO("willian@gmail.com", "teste");

        String accessToken = given().basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user).when().post()
                .then().statusCode(200)
                .extract().body().as(TokenDTO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/v1/releases")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test when create should return Release Object")
    public void testCreate() throws IOException {
        mockRelease();

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SITE)
                .body(releasesDTO).when().post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        ReleasesDTO createdRelease = objectMapper.readValue(content, ReleasesDTO.class);
        releasesDTO = createdRelease;
        assertNotNull(createdRelease, () -> "Created Release Should not null");

        assertNotNull(createdRelease.getId(), () -> "Created Release Id Should not null");
        assertNotNull(createdRelease.getValue(), () -> "Created Release value Should not null");
        assertNotNull(createdRelease.getDescription(), () -> "Created Release description Should not null");

        assertTrue(createdRelease.getId() > 0, () ->  "The Created Release Id should be bigger then 0");

        assertEquals("Arroz", createdRelease.getDescription(), () -> "Created Release Description and release description Should be the same!");
        assertEquals(ReleasesTypes.RECEITAS, createdRelease.getType(), () -> "Created Release type and release type Should  be the same!");
        assertEquals(ReleasesStatus.PENDENTE, createdRelease.getStatus(), () -> "Created Release status and release status Should  be the same!");
    }

    @Test
    @Order(2)
    @DisplayName("Test when find by id should return Release Object")
    public void testFindById() throws IOException {

        mockRelease();

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SITE)
                .pathParams("id", releasesDTO.getId()).when().get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        ReleasesDTO createdRelease = objectMapper.readValue(content, ReleasesDTO.class);
        releasesDTO = createdRelease;
        assertNotNull(createdRelease, () -> "Created book Should not null");

        assertNotNull(createdRelease.getId(), () -> "Created Release Id Should not null");
        assertNotNull(createdRelease.getValue(), () -> "Created Release value Should not null");
        assertNotNull(createdRelease.getDescription(), () -> "Created Release description Should not null");

        assertTrue(createdRelease.getId() > 0, () ->  "The Created Release Id should be bigger then 0");

        assertEquals("Arroz", createdRelease.getDescription(), () -> "Created Release Description and release description Should be the same!");
        assertEquals(ReleasesTypes.RECEITAS, createdRelease.getType(), () -> "Created Release type and release type Should  be the same!");
        assertEquals(ReleasesStatus.PENDENTE, createdRelease.getStatus(), () -> "Created Release status and release status Should  be the same!");
    }

    @Test
    @Order(3)
    @DisplayName("Test when update should return Release Object")
    public void testUpdate() throws IOException {

        releasesDTO.setDescription("Feijão");
        releasesDTO.setUserId(null);
        releasesDTO.setRegistrationDate("");
        releasesDTO.setStatus(null);

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(releasesDTO).when().patch()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        ReleasesDTO createdBook = objectMapper.readValue(content, ReleasesDTO.class);
        releasesDTO = createdBook;
        assertNotNull(createdBook, () -> "Update Person Should not null");

        assertEquals(releasesDTO.getId(), createdBook.getId(), () ->  "The ReleaseId should the same from created Release");

        assertNotNull(createdBook, () -> "created Book Should not null");

        assertNotNull(createdBook.getId(), () -> "created release Id Should not null");
        assertNotNull(createdBook.getValue(), () -> "created release price Should not null");
        assertNotNull(createdBook.getDescription(), () -> "created release description Should not null");

        assertTrue(createdBook.getId() > 0, () ->  "The Release Id should be bigger then 0");

        assertEquals("Feijão", createdBook.getDescription(), () -> "created release description and release description Should be the same!");
        assertEquals(2024, createdBook.getYear(), () -> "created release year and release year Should  be the same!");
    }

    @Test
    @Order(5)
    @DisplayName("Test when delete")
    public void testDelete() throws IOException {

        given().spec(specification)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SITE)
                .pathParams("id", releasesDTO.getId()).when().delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    @DisplayName("Test when find all should return a list of Releases Object")
    public void testFindAll() throws IOException {

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParam("page",0, "size", 10, "direction", "asc")
                .body(releasesDTO).when().get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperReleaseDTO wrapper = objectMapper.readValue(content, WrapperReleaseDTO.class);

        List<ReleasesDTO> releasesDTOS = wrapper.getEmbeddedDTO().getReleasesDTOList();

        ReleasesDTO findBook1 = releasesDTOS.getFirst();
        assertNotNull(findBook1.getId(), () -> "Release Id Should not null");
        assertNotNull(findBook1.getDescription(), () -> "Release description Should not null");
        assertNotNull(findBook1.getValue(), () -> "Release price Should not null");

        ReleasesDTO findBook = releasesDTOS.get(1);

        assertNotNull(findBook.getId(), () -> "Release Id Should not null");
        assertNotNull(findBook.getDescription(), () -> "Release description Should not null");
        assertNotNull(findBook.getValue(), () -> "Release price Should not null");
    }

    private void mockRelease() {
        releasesDTO.setDescription("Arroz");
        releasesDTO.setMonth(5);
        releasesDTO.setType(ReleasesTypes.RECEITAS);
        releasesDTO.setYear(2024);
        releasesDTO.setUserId(1L);
        releasesDTO.setValue(BigDecimal.TEN);
        releasesDTO.setRegistrationDate("2024-04-01T00:00:00Z");
    }
}
