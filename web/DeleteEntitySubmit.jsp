<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: semik
  Date: 06.04.2020
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        Integer elementID = Integer.parseInt(request.getParameter("entityID"));
        String entityName = request.getParameter("entity");
    %>
    <c:set scope="request" value="${requestScope.get('success')}" var="successDelete"/>
    <title>Deleting <%=elementID%> element of <%=entityName%></title>
    <style>
        <%@include file="styles/global.css"%>
    </style>
</head>
<body>
    <header>Deleting <%=elementID%> element of <%=entityName%></header>
    <h1>
        <c:if test="${successDelete}">
            Deleted successfully!
        </c:if>
        <c:if test="${!successDelete}">
            Deletion failure!
        </c:if>
    </h1>
</body>
</html>
