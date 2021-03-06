<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="database.GymDB" %>
<%@ page import="logic.gym.*" %><%--
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
    <%
        request.setAttribute("factories", new GymDB().getDBEntityFactories());
    %>
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
        <form action="/get-entities" method="get" style="display: flex; flex-direction: column;">
            <c:forEach var="factory" items="${factories}">
                <button class="big-a" name="entity" value="${factory.create().getTableID()}" type="submit">
                    ${factory.create().getTableID()}
                </button>
            </c:forEach>
<%--            <button class="big-a" name="entity" value="<%=new GymFactory().create().getTableID()%>" type="submit">Gyms</button>--%>
<%--            <button class="big-a" name="entity" value="<%=new GymSectionFactory().create().getTableID()%>" type="submit">Gym Sections</button>--%>
<%--            <button class="big-a" name="entity" value="<%=new InfoFactory().create().getTableID()%>" type="submit">Infos</button>--%>
        </form>
        <% } else { %>
            <p>Database doesn't exist you need to <a href="/db-create">create it</a></p>
        <% } %>
        <h2>Options</h2>
        <a class="big-a" href="/db-drop">Drop DataBase</a>
        <a class="big-a">Stop Server</a>
    </div>
</body>
</html>
