package com.willian.financial_organizer.controllers;

import com.willian.financial_organizer.configs.TestConfigs;
import com.willian.financial_organizer.dtos.security.AccountCredentialsDTO;
import com.willian.financial_organizer.dtos.security.TokenDTO;
import com.willian.financial_organizer.integrationsTest.testContainers.AbstractIntegrationTest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class AuthControllerTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static TokenDTO tokenDTO;

    @Test
    @Order(1)
    public void testSignin() throws IOException {
        AccountCredentialsDTO user = new AccountCredentialsDTO("willian@gmail.com", "teste");

        tokenDTO = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user).when().post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class);

        assertNotNull(tokenDTO.getAccessToken(), ()-> "Access Token should not null!");
        assertNotNull(tokenDTO.getRefreshToken(), ()-> "Refresh Token should not null!");
    }

    @Test
    @Order(2)
    public void testRefresh() throws IOException {

        System.out.println(tokenDTO.getUserName());

        TokenDTO newtokenDTO = given()
                .basePath("/auth")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParams("username", tokenDTO.getUserName())
                .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer "
                        + tokenDTO.getRefreshToken()).when().put("refresh/{username}", tokenDTO.getUserName())
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDTO.class);

        assertNotNull(newtokenDTO.getAccessToken(), ()-> "Access Token should not null!");
        assertNotNull(newtokenDTO.getRefreshToken(), ()-> "Refresh Token should not null!");
    }

}
