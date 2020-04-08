<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database.*" %><%--
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
        List<IDBEntity> entities = (ArrayList)request.getAttribute("entities");
        request.setAttribute("entities", entities);
        DBEntity templateEntity = (DBEntity)request.getAttribute("templateEntity");
        IDBEntityFactory entityFactory = (IDBEntityFactory)request.getAttribute("entityFactory");
        request.setAttribute("entityFactory", entityFactory);
        DBEntity selected = (DBEntity) request.getAttribute("selected");
        String tableID = templateEntity.getTableID();
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

        .selected {
            background: #2680ff;
            color:white;
        }

        #top-buttons-bar {
            display: flex;
            flex-direction: row;
            margin-bottom: 10px;
            border-top: 1px solid white;
            align-self: stretch;
        }
        #top-buttons-bar > button{
            flex:1;
            border:none;
            outline: none;
            background: #3540ff;
            color:white;
            font-size: 1.2em;
            padding: 10px;
        }
        #top-buttons-bar > button:hover {
            background: #3a6be6;
            cursor: pointer;
        }
    </style>
    <script>
        function addParameter(name, value, url, params) {
            var urlParam = new URLSearchParams(params);
            if(!urlParam.get(name))
                urlParam.append(name, value);
            return url + '?' + urlParam;
        }
    </script>
</head>
<body>
    <header><%= tableID %> list</header>
    <c:if test="${templateEntity.getDatabase().isDBcreated()}">
        <div id="top-buttons-bar">
            <c:if test="${templateEntity.isAddable()}">
                <button type="submit" name="entity" value="<%=tableID%>"
                        onclick="window.location
                                .replace('/add-entity?entity=<%=tableID%>')"
                >add ➕</button>
            </c:if>

            <c:if test="${selected!=null}">
                <c:if test="${selected.isDeletable()}">
                    <button type="submit" name="entity" value="<%=tableID%>"
                    onclick="window.location
                            .replace('/delete-entity?entity=<%=tableID%>&entityID=<%=selected.getEntityIDValue()%>')"
                    >delete ➖</button>
                </c:if>

                <c:if test="${selected.isEditable()}">
                    <button type="submit" name="entity" value="<%=tableID%>">edit ✍</button>
                </c:if>
            </c:if>
        </div>
    </c:if>
    <c:if test="${entities!=null}">
    <table>
        <tr>
                <c:forEach var="dbValue" items="${entityFactory.create().getVariablesWithID()}">
                    <th><c:out value="${dbValue.getTitle()}"></c:out></th>
                </c:forEach>
        </tr>
        <c:forEach var="entity" items="${entities}">
            <tr
                    class="<c:if test="${selected!=null && entity.equals(selected)}">selected</c:if>"
                    onclick="window.location.replace(addParameter('selected', '${entity.getEntityIDValue()}',
                             window.location.pathname, window.location.search))">
                <c:forEach var="dbValue" items="${entity.getVariablesWithID()}">
                    <td><c:out value="${dbValue.getValue()}"/></td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
    </c:if>
</body>
</html>
