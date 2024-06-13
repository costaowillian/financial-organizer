package com.willian.financial_organizer.integrationsTest.testContainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.stream.Stream;
import java.util.Map;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.29");

        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }

        private Map<String, String> createConnectionConfigurations() {
            return Map.of(
                    "spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword()
            );
        }

        @Override
        @SuppressWarnings({"rawtyoes", "unchecked"})
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource("testContainers", (Map) createConnectionConfigurations());
            configurableEnvironment.getPropertySources().addFirst(testContainers);
        }
    }
}
