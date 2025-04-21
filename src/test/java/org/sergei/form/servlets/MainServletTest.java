package org.sergei.form.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Testcontainers
public class MainServletTest {

    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("messages")
            .withUsername("user")
            .withPassword("admin");

    private static EntityManagerFactory emf;
    private static MainServlet servlet;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @BeforeAll
    static void setUp() {
        // Ensures that container is up
        mysql.start();

        // Parameters for java persistence
        Map<String, String> props = Map.of(
                "jakarta.persistence.jdbc.url", mysql.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", mysql.getUsername(),
                "jakarta.persistence.jdbc.password", mysql.getPassword(),
                "jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver",
                "hibernate.dialect", "org.hibernate.dialect.MySQLDialect",
                "hibernate.hbm2ddl.auto", "create-drop",
                "hibernate.show_sql", "true"
        );

        emf = Persistence.createEntityManagerFactory("messages-persistence-unit-test", props);

        // Initializes servlet
        servlet = new MainServlet();

    }


    @AfterAll
    static void tearDown() {
        if (emf != null) emf.close();
        mysql.stop();
    }


    @Test
    public void testDoPost() throws Exception {
        // JSON-request
        String json = """
        {
            "username": "John",
            "email": "john@example.com",
            "message": "Hello from test"
        }
        """;

        // Mocks JSON request
        HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        // Мocks JSON response
        StringWriter responseWriter = new StringWriter();
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Creates entity manager
        EntityManager em = emf.createEntityManager();

        // Creates servlet with substituted entity manager factory
        MainServlet servlet = new MainServlet() {
            protected EntityManagerFactory getEntityManagerFactory() {
                return emf;
            }
        };

        // Calls doPost
        servlet.doPost(request, response);

        // Checking the answer
        String responseJson = responseWriter.toString();
        assertTrue(responseJson.contains("\"status\":"));
        assertTrue(responseJson.contains("OK"));

        em.close();
        emf.close();
    }
}
