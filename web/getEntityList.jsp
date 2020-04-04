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
        table {
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        tr:hover > td {
            background: #bcbcbc;
            color:white;
        }
        td{
            text-align: center;
        }
    </style>
</head>
<body>
    <header><%= tableID %> list</header>
    <c:if test="${entities!=null}">
    <table>
        <tr>
            <c:forEach var="entitiy" items="${entities}">
<%--                <c:out value="${entitiy.getVariables()}"--%>
                <c:forEach var="dbValue" items="${entitiy.getVariables()}">
                    <th><c:out value="${dbValue.getTitle()}"></c:out></th>
                </c:forEach>
            </c:forEach>
        </tr>
        <c:forEach var="entity" items="${entities}">
            <tr>
                <c:forEach var="dbValue" items="${entity.getVariables()}">
                    <td><c:out value="${dbValue.getValue()}"/></td>
<%--                    <%=entities.get(0).getVariables().get(0).getValue()%>--%>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
    </c:if>
</body>
</html>
