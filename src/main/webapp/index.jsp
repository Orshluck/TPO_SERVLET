<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- THIS WHOLE PAGE IS AI GENERATED, I DO UNDERSTAND MOST OF IT THOUGH -->
<!DOCTYPE html>
<html>
<head>
    <title>Song Search</title>
    <style>
        /* Colors */
        :root {
            --primary-color: #22577A;
            --secondary-color: #38A3A5;
            --background-color: #48B89F;
            --text-color: #333;
            --label-color: #50C29C;
            --input-border-color: #57CC99;
            --submit-button-background-color: var(--primary-color);
            --submit-button-text-color:#fff;
            --submit-button-hover-color: #22577A;
            --result-heading-color: var(--text-color);
            --result-text-color: var(--label-color);
        }

        /* Rest of the styles */
        body {
            font-family: Arial, sans-serif;
            background-color: var(--background-color);
            margin: 20px;
        }

        h1 {
            color: var(--text-color);
            text-align: center;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: var(--label-color);
        }

        input[type="text"],
        select,
        input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid var(--input-border-color);
            border-radius: 3px;
            font-size: 14px;
            margin-bottom: 10px;
        }

        select[multiple] {
            height: 120px;
        }

        input[type="submit"] {
            background-color: var(--submit-button-background-color);
            color: var(--submit-button-text-color);
            padding: 10px 20px;
            border: none;
            border-radius: 3px;
            font-size: 14px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: var(--submit-button-hover-color);
        }

        .result-container {
            background-color: #f2f2f2;
            padding: 20px;
            border-radius: 5px;
        }

        .result-heading {
            color: #333333;
            font-size: 20px;
            margin-bottom: 10px;
        }

        .result-list {
            list-style-type: none;
            padding: 0;
        }

        .result-item {
            color: #666666;
            font-size: 16px;
            margin-bottom: 5px;
            cursor: pointer;
        }

        .result-item:hover {
            background-color: #e9e9e9;
        }
    </style>
</head>
<body>
<h1>Song Search</h1>
<form action="SearchServlet" method="POST">
    <label for="searchText">Search:</label>
    <input type="text" id="searchText" name="searchText">
    <br><br>

    <label for="searchType">Search By:</label>
    <select id="searchType" name="searchType">
        <option value="any">Any</option>
        <option value="name">Name</option>
        <option value="band">Band</option>
        <option value="album">Album</option>
    </select>
    <br><br>

    <label for="year">Year:</label>
    <select id="year" name="year">
        <script>
            var currentYear = new Date().getFullYear();
            var selectElement = document.getElementById("year");

            // Add "Choice Any" option at the top
            var anyOption = document.createElement("option");
            anyOption.value = "";
            anyOption.text = "Any";
            selectElement.appendChild(anyOption);

            for (var y = 1990; y <= currentYear; y++) {
                var option = document.createElement("option");
                option.value = y;
                option.text = y;
                selectElement.appendChild(option);
            }
        </script>
    </select>
    <br><br>

    <label for="genre">Genre:</label>
    <select id="genre" name="genre" multiple>
        <option value="any">Any</option>
        <option value="pop">Pop</option>
        <option value="rock">Rock</option>
        <option value="hiphop">Hip Hop</option>
        <option value="country">Country</option>
        <!-- Add more genres here -->
    </select>
    <br><br>

    <label for="instrument">Instrument:</label>
    <select id="instrument" name="instrument" multiple>
        <option value="any">Any</option>
        <option value="guitar">Guitar</option>
        <option value="piano">Piano</option>
        <option value="drums">Drums</option>
        <option value="bass">Bass</option>
        <!-- Add more instruments here -->
    </select>
    <br><br>

    <label for="duration">Duration:</label>
    <input type="number" id="duration" name="duration" min="30" max="480" step="30">
    seconds
    <br><br>

    <input type="submit" value="Search">
</form>

<%-- Retrieve and display the result --%>
<% List<String> resultList = (List<String>) request.getAttribute("result"); %>
<% if (resultList != null && !resultList.isEmpty()) { %>
<div class="result-container">
    <h3 class="result-heading">Search Result:</h3>
    <ul class="result-list">
        <% for (String result : resultList) { %>
        <li class="result-item"><%= result %></li>
        <% } %>
    </ul>
</div>
<% } %>

</body>
</html>
