package com.willian.financial_organizer.integrationsTest.swagger;

import com.willian.financial_organizer.configs.TestConfigs;
import com.willian.financial_organizer.integrationsTest.testContainers.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @DisplayName("Integration test for Swagger")
    @Test
    public void showDisplaySwaggerUiPage() {
        String content = given().basePath("/swagger-ui/index.html")
                .port(TestConfigs.SERVER_PORT)
                .when().get()
                .then().statusCode(200)
                .extract().body().asString();
        assertTrue(content.contains("Swagger UI"), () -> "Should contains the string Swagger UI");
    }
}
