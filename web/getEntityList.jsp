<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="database.DBEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: semik
  Date: 03.04.2020
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        List<DBEntity> entities = (ArrayList)request.getAttribute("entities");
        String tableID = entities == null ? "Empty" : entities.get(0).getTableID();
    %>
    <title><%= tableID %> List</title>
    <style>
        <%@include file="styles/global.css"%>
    </style>
</head>
<body>
    <header><%= tableID %> list</header>
    <c:if test="${entities!=null}">
        <c:forEach var="entity" items="${entities}">
            <c:out value="${String.format('%s. %s', entity.getEntityID(), entity.getDisplayValue())}"/>
        </c:forEach>
    </c:if>
</body>
</html>
