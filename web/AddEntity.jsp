<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="database.GymDB" %>
<%@ page import="database.DBEntity" %>
<%@ page import="database.DBValue" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: semik
  Date: 06.04.2020
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Entity of Table <%=request.getParameter("entity")%></title>
    <style>
        <%@include file="styles/global.css"%>
        #add-entity-form {
            display: flex;
            flex-direction: column;
        }
        #add-entity-form > input {
            margin:10px 5px;
        }
        #add-entity-form > button {
            padding: 10px;
            color:white;
            background: #3540ff;
        }
        #add-entity-form > button:hover {
            background: #2549ff;
            cursor: pointer;
        }
        #add-entity-form > button:disabled {
            background: gray;
        }
    </style>
</head>
<body>
    <%
        GymDB db = new GymDB();
        DBEntity templateEntity = db.getEmptyEntity(request.getParameter("entity"));
        List<DBValue> values = templateEntity.getVariables();
        request.setAttribute("values", values);
    %>
    <c:if test="${requestScope.get('successCode')==null}">
        <form id="add-entity-form" method="post" action="/add-entity/submit">
            <c:forEach var="dbValue" items="${values}">
                <input value="${dbValue.getValue()}" placeholder="${dbValue.getTitle()}" name="${dbValue.getTitle()}"/>
            </c:forEach>
            <input type="hidden" value="<%=request.getParameter("entity")%>" name="entity" />
            <button type="submit">Add</button>
        </form>
    </c:if>
    <c:if test="${requestScope.get('successCode')!=null}">
        <c:if test="${requestScope.get('successCode')=='success'}">
            <h2>Added entity successfully!</h2>
        </c:if>
        <c:if test="${requestScope.get('successCode')!='success'}">
            <h2>Error occupied while adding entity...</h2>
        </c:if>
        <a href="/">go back</a>
    </c:if>
</body>
</html>
