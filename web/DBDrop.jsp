<%@ page import="database.GymDB" %><%--
  Created by IntelliJ IDEA.
  User: semik
  Date: 02.04.2020
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Database Drop</title>
</head>
<style>
    <%@include file="styles/global.css"%>
</style>
<body>
    <header>Database drop</header>
    <% if (!new GymDB().isDBcreated()) { %>
        <h2>Database not created</h2>
    <% }
    if(request.getAttribute("drop success")==null && new GymDB().isDBcreated()) { %>
        <h2>Database exist</h2>
        <form action="/db-drop/submit" method="post">
            <button type="submit">drop database</button>
        </form>
    <% } else if(request.getAttribute("drop success")!=null) { %>
        <h2>Database dropped successfully!</h2>
        <a href="/">go back</a>
    <% } %>
</body>
</html>
