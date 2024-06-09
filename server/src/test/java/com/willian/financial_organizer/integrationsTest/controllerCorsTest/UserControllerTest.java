package com.willian.financial_organizer.integrationsTest.controllerCorsTest;

import com.willian.financial_organizer.configs.TestConfigs;
import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import com.willian.financial_organizer.dtos.security.TokenDTO;
import com.willian.financial_organizer.integrationsTest.testContainers.AbstractIntegrationTest;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    static BigDecimal balance;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        balance = BigDecimal.ONE;
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
                .setBasePath("/api/v1/user")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test when find by id with valid origin localhost:3000")
    public void testFindById() throws IOException {

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SITE)
                .pathParams("id", 1L).when().get("{id}/balance")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        BigDecimal result = objectMapper.readValue(content, BigDecimal.class);
        assertNotNull(result, () -> "Created Release Should not null");

    }

    @Test
    @Order(2)
    @DisplayName("Test when find by id with non valid origin willian.com.br")
    public void testFindByIdWithWrongOrigin() throws IOException {

        String content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .pathParams("id", 1L).when().get("{id}/balance")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertNotNull(content, () -> "Should return error 403");

        assertEquals("Invalid CORS request", content, () -> "Should contains Invalid CORS request!");
    }

}
