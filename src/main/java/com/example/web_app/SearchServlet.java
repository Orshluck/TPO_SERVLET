package com.example.web_app;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Server.Server;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private String message;
    Server server;

    public void init() {
        server = new Server();
        server.generateTables();
        try {
            server.seed2();
        } catch (Exception e) {
            System.out.println("error while seeding");
            e.printStackTrace();
            System.out.println("error up");
            //i should print error, but here is just error with trying 2 to insert, i could do bool server seeded
        }



    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String searchText = request.getParameter("searchText");
        String searchType = request.getParameter("searchType");
        String year = request.getParameter("year");
        //String[] genres = request.getParameterValues("genre");
        String genres = request.getParameter("genre");
        String instruments = request.getParameter("instrument") ;
        String duration = request.getParameter("duration") ;

        // Perform necessary processing
        // ...

        List<String> result = new ArrayList<>();
        System.out.println(searchType);
        switch (searchType) {
            case "any":

            case "name":

                result.add("Name choice");

                break;
            case "band":
                String [] bands = server.getSongsByBand(searchText);
                result.add("Band choice");
                break;
            case "album":
                result.add("Album choice");
                break;
            default:
                result.add("Empty choice choice");
                break;
        }

        result.add("PRACA PRACA");
        result.add("Już idę Panie");
        result.add("Coś trza robić?");
        // Set the result as a request attribute
        request.setAttribute("result", result);

        // Forward the request to index.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {
        server.CloseServer();
    }
}