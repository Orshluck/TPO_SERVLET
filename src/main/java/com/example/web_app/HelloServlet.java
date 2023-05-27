package com.example.web_app;

import java.io.*;
import java.sql.*;
import java.util.List;

import Server.Server;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "hello-Servlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    Server server;
    public void init() {
        server = new Server();
        server.generateTables();
        try {
            server.seed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");


//        List<String> test = server.getInstruments();
//        for (String instrument : test
//             ) {
//            response.getWriter().println(instrument);
//            response.getWriter().println("<br>");
//        }

        List<String> options = server.getInstruments();; // Replace `yourList` with your actual list of strings

// Generate the HTML for the dropdown options
        StringBuilder optionsHtml = new StringBuilder();
        for (String option : options) {
            optionsHtml.append("<option value=\"").append(option).append("\">").append(option).append("</option>");
        }

// Create the HTML for the dropdown section
        String html = "<select>" + optionsHtml.toString() + "</select>";

// Return the HTML response

        response.getWriter().write(html);



        // Button 1
        out.println("<form method=\"GET\">");
        out.println("<input type=\"submit\" name=\"button1\" value=\"Button 1\">");
        out.println("</form>");

        // Button 2
        out.println("<form method=\"GET\">");
        out.println("<input type=\"submit\" name=\"button2\" value=\"Button 2\">");
        out.println("</form>");

        // Button 3
        out.println("<form method=\"GET\">");
        out.println("<input type=\"submit\" name=\"button3\" value=\"Button 3\">");
        out.println("</form>");

        // Check button parameter and display corresponding message
        if (request.getParameter("button1") != null) {
            out.println("Button 1 was clicked");
        } else if (request.getParameter("button2") != null) {
            out.println("Button 2 was clicked");
        } else if (request.getParameter("button3") != null) {
            out.println("Button 3 was clicked");
        } else {
            // No button was clicked
            out.println("No button was clicked");
        }




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("button 1") != null) {
            response.getWriter().println("Button1");
        } else if (request.getParameter("button2") != null) {
            response.getWriter().println("Button 2");
        } else if (request.getParameter("button3") != null) {
            response.getWriter().println("Button 3");
        } else {
            // ???
        }

        request.getRequestDispatcher("/WEB-INF/some-result.jsp").forward(request, response);
    }

    public void destroy() {
    }
}