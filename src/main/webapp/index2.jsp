<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Search</title>
    <style>
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        select {
            width: 200px;
            margin-bottom: 10px;
        }

        input[type="number"] {
            width: 60px;
        }

        input[type="submit"] {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h1>Song Search</h1>
<form action="SearchServlet" method="POST">
    <label for="searchText">Search:</label>
    <input type="text" id="searchText" name="searchText">
    <br>

    <label for="searchType">Search By:</label>
    <select id="searchType" name="searchType">
        <option value="Any" selected>Any</option>
        <option value="Name">Name</option>
        <option value="Band">Band</option>
        <option value="Album">Album</option>
    </select>
    <br>

    <label for="year">Year:</label>
    <select id="year" name="year">
        <option value="" selected>Any</option>
        <script>
            var currentYear = new Date().getFullYear();
            var selectElement = document.getElementById("year");
            for (var y = 1990; y <= currentYear; y++) {
                var option = document.createElement("option");
                option.value = y;
                option.text = y;
                selectElement.appendChild(option);
            }
        </script>
    </select>
    <br>

    <label for="genre">Genre:</label>
    <select id="genre" name="genre" multiple>
        <option value="" selected>Any</option>
        <option value="Pop">Pop</option>
        <option value="Rock">Rock</option>
        <option value="HipHop">Hip Hop</option>
        <option value="Country">Country</option>
        <!-- Add more genres here -->
    </select>
    <br>

    <label for="instrument">Instrument:</label>
    <select id="instrument" name="instrument" multiple>
        <option value="" selected>Any</option>
        <option value="Guitar">Guitar</option>
        <option value="Piano">Piano</option>
        <option value="Drums">Drums</option>
        <option value="Bass">Bass</option>
        <!-- Add more instruments here -->
    </select>
    <br>

    <label for="duration">Duration:</label>
    <input type="number" id="duration" name="duration" min="30" max="480" step="30">
    seconds
    <br>

    <input type="submit" value="Search">
</form>
<%-- Retrieve and display the result --%>
<% List<String> resultList = (List<String>) request.getAttribute("result"); %>
<% if (resultList != null && !resultList.isEmpty()) { %>
<div>
    <h3>Search Result:</h3>
    <ul>
        <% for (String result : resultList) { %>
        <li><%= result %></li>
        <% } %>
    </ul>
</div>
<% } %>
</body>
</html>
