package org.sergei.form.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sergei.form.entities.SForm;
import org.sergei.form.dto.ResponseData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Main servlet that handles both GET and POST requests related to form submissions.
 *
 * <p>POST: Accepts JSON-formatted form data, converts it to a Java entity,
 * and stores it in the database using JPA.</p>
 *
 * <p>GET: Returns all stored form entries in JSON format.</p>
 */
@WebServlet("/form")
public class MainServlet extends HttpServlet {

    /** Gson instance for JSON serialization/deserialization. */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /** Singleton factory for creating JPA EntityManager instances. */
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("messages-persistence-unit");

    /**
     * Handles GET requests.
     * Returns all form entries as a JSON array.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        EntityManager em = entityManagerFactory.createEntityManager();
        List<SForm> formData = em.createQuery("SELECT f FROM SForm f", SForm.class).getResultList();
        em.close();

        String jsonResponse = gson.toJson(formData);

        try (PrintWriter writer = response.getWriter()) {
            writer.print(jsonResponse);
            writer.flush();
        }
    }

    /**
     * Handles POST requests.
     * Receives a form in JSON format, converts it to a Java object,
     * persists it to the database, and sends back a confirmation response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 1. Read JSON from request body
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        String requestBody = builder.toString();
        System.out.printf("Received JSON: %s%n", requestBody);

        // 2. Convert JSON to Java entity
        SForm requestData = gson.fromJson(requestBody, SForm.class);
        System.out.println("Parsed Entity: " + requestData);

        // 3. Persist entity to the database
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(requestData); // merge allows upsert-like behavior
        entityManager.getTransaction().commit();
        entityManager.close();

        // 4. Prepare response DTO
        ResponseData responseData = new ResponseData("OK", "Your data has been saved.");

        // 5. Convert response DTO to JSON
        String jsonResponse = gson.toJson(responseData);

        // 6. Send response back to the client
        try (PrintWriter writer = response.getWriter()) {
            writer.print(jsonResponse);
            writer.flush();
        }
    }

    /**
     * Ensures proper cleanup when the servlet is destroyed.
     * Closes the shared EntityManagerFactory.
     */
    @Override
    public void destroy() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
