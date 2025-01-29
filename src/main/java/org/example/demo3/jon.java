package org.example.demo3;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "jonServlet", value = "/hello-servlet")
public class jon extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DATABASE()")) {

            out.println("<html><body>");
            out.println("<h1>Connected to Database: </h1>");
            if (rs.next()) {
                out.println("<h2>" + rs.getString(1) + "</h2>");
            }
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<html><body><h1>Database Connection Failed!</h1>");
            out.println("<p>Error: " + e.getMessage() + "</p></body></html>");
        }
    }
}
