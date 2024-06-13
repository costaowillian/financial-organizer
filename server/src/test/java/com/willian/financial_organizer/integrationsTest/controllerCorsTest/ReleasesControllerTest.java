package com.willian.financial_organizer.integrationsTest.controllerCorsTest;

import com.willian.financial_organizer.configs.TestConfigs;
import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import com.willian.financial_organizer.dtos.security.TokenDTO;
import com.willian.financial_organizer.integrationsTest.dtos.ReleasesDTO;
import com.willian.financial_organizer.integrationsTest.testContainers.AbstractIntegrationTest;
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

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ReleasesControllerTest extends AbstractIntegrationTest {
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
    @DisplayName("Test when create with valid origin localHost:3000!")
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
    @DisplayName("Test when create with non valid origin willian.com.br")
    public void testCreateWithWrongOrigin() throws IOException {
        mockRelease();

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .body(releasesDTO).when().post()
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();


        assertNotNull(content, () -> "Should return error 403");

        assertEquals("Invalid CORS request", content, () -> "Should contains Invalid CORS request!");
    }

    @Test
    @Order(3)
    @DisplayName("Test when find by id with valid origin localhost:3000")
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
    @Order(4)
    @DisplayName("Test when find by id with non valid origin willian.com.br")
    public void testFindByIdWithWrongOrigin() throws IOException {

        mockRelease();

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .pathParams("id", releasesDTO.getId()).when().get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content, () -> "Should return error 403");

        assertEquals("Invalid CORS request", content, () -> "Should contains Invalid CORS request!");
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
