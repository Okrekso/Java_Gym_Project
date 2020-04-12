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
    <%
        GymDB db = new GymDB();
        String mode = (String) request.getAttribute("mode");
        Integer id = mode == "edit" ? Integer.parseInt(request.getParameter("id")) : null;
        List<DBValue> params = (List<DBValue>) request.getAttribute("editVariables");
        DBEntity templateEntity = db.getEmptyEntity(request.getParameter("entity"));
        List<DBValue> values = mode == "edit" ? params : templateEntity.getVariables();
        request.setAttribute("values", values);
        request.setAttribute("db", new GymDB());
    %>
    <title><%=mode=="edit" ? "Edit" : "New"%> Entity of Table
        <%=request.getParameter("entity")%> <%=mode=="edit" ? String.format("of ID #%s", id) : ""%></title>
    <style>
        <%@include file="styles/global.css"%>
        #add-entity-form {
            display: flex;
            flex-direction: column;
            padding: 10px;
        }
        #add-entity-form > input {
            margin-bottom: 10px;
        }
        #add-entity-form > button {
            padding: 10px;
            margin-top: 20px;
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
        #add-entity-form > p {
            color:gray;
            margin: 0;
        }
    </style>
</head>
<body>
    <c:if test="${requestScope.get('successCode')==null}">
        <form id="add-entity-form" method="post" action="/<%=mode=="edit"?"edit":"add"%>-entity/submit">
            <c:forEach var="dbValue" items="${values}">
                <p>${dbValue.getTitle()}</p>
                <c:if test="${dbValue.isForeignKey()}">
                    <select name="${dbValue.getTitle()}">
                        <c:forEach var="selectEntity" items="${db.getFromEntityTable(dbValue.getForeignKeyFactory())}">
                            <option value="${selectEntity.getEntityID().getValue()}">${selectEntity.toString()}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <c:if test="${!dbValue.isForeignKey()}">
                    <input value="${dbValue.getValue()}" placeholder="${dbValue.getTitle()}" name="${dbValue.getTitle()}"/>
                </c:if>
            </c:forEach>
            <input type="hidden" value="<%=request.getParameter("entity")%>" name="entity" />
            <button type="submit"><%=mode=="edit" ? "save Entity" : "Add Entity"%></button>
        </form>
    </c:if>
    <c:if test="${requestScope.get('successCode')!=null}">
        <c:if test="${requestScope.get('successCode')=='e-sucess'}">
            <h2>Edited entity successfully!</h2>
        </c:if>
        <c:if test="${requestScope.get('successCode')=='success'}">
            <h2>Added entity successfully!</h2>
        </c:if>
        <c:if test="${requestScope.get('successCode')=='error'}">
            <h2>Error occupied while adding entity...</h2>
        </c:if>
        <a href="/">go back</a>
    </c:if>
</body>
</html>
