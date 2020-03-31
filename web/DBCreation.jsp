<%@ page import="database.GymDB" %><%--
  Created by IntelliJ IDEA.
  User: semik
  Date: 31.03.2020
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Database Initialization</title>
</head>
<style>
    <%@include file="styles/global.css"%>
    #db-status {
        color:red;
    }
</style>
<body>
    <header>Database creation</header>
    <p><%= request.getAttribute("message") == null ? "database status:" : request.getAttribute("message") %></p>
    <div id="db-status">
    <%

        GymDB db = new GymDB();
        if(db.isDBcreated()) { %>
        <p>Database exist!</p>
        <a href="/">go back</a>
        <%   } else { %>
        <form method="post" action="/db-create/submit">
            <p>Looks like database doesn't create yet.</p>
            <button type="submit" name="create-DB">Create DB</button>
        </form>
    <% } %>
    </div>
</body>
</html>
