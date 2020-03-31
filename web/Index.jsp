<%@ page import="logic.gym.Gym" %>
<%@ page import="database.GymDB" %><%--
  Created by IntelliJ IDEA.
  User: semik
  Date: 31.03.2020
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GymDB System</title>
</head>
<style>
    <%@include file="styles/global.css"%>
    .big-a{
        color:white;
        margin: 10px 10px;
        padding: 10px;
        background: #3540ff;
        font-size: 1.2em;
    }
    .big-a:hover {
        background: #3a6be6;
        cursor: pointer;
    }
    h2 {
        border-bottom: 1px solid black;
        font-size: 1.3em;
    }
</style>
<body>
    <header>GymDB System</header>
    <div id="links-block">
        <a class="big-a" href="/db-create">Database Creation</a>
        <h2>Database Entities</h2>
        <% if (new GymDB().isDBcreated()) { %>
            <a class="big-a" >Gyms</a>
            <a class="big-a" >Gyms Sections</a>
        <a class="big-a" >Gyms Sections</a>
        <% } else { %>
            <p>Database doesn't exist you need to <a href="/db-create">create it</a></p>
        <% } %>
    </div>
</body>
</html>
