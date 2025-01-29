package org.example.demo3;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ToyServlet", value = "/toy")
public class ToyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Read JSON input
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Extract 'type' value
        String toyType = requestBody.toString().replaceAll("[^a-zA-Z0-9 ]", ""); // Basic sanitization

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO toy (type) VALUES (?)")) {

            stmt.setString(1, toyType);
            int rowsInserted = stmt.executeUpdate();

            PrintWriter out = response.getWriter();
            out.println("{\"status\": 200, \"message\": \"Toy added successfully\", \"rowsInserted\": " + rowsInserted + "}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.println("{\"status\": 500, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }
}
