package com.hendisantika;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
public class MySQLContainerTest {

    // Define the MySQL container with a valid MySQL version
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("shopDB")
            .withUsername("yu71")
            .withPassword("53cret");
    @Autowired
    private DataSource dataSource;

    // Configure Spring Boot to use the Testcontainer's MySQL instance
    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Test
    void testMySQLContainerIsRunning() {
        // Verify that the container is running
        assertTrue(mySQLContainer.isRunning(), "MySQL container should be running");

        System.out.println("MySQL container is running on port: " + mySQLContainer.getFirstMappedPort());
        System.out.println("JDBC URL: " + mySQLContainer.getJdbcUrl());
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        // Test that we can connect to the database and execute a simple query
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {

            assertTrue(resultSet.next(), "Result set should have at least one row");
            assertEquals(1, resultSet.getInt(1), "The query should return 1");
        }
    }
}