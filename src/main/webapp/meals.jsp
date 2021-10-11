<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>

<html>
<head>
    <title>Meals</title>
    <style>
        table, th, td{
            border: 2px solid black;
            border-collapse: collapse;
        }
        th, td{
            padding: 5px;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:choose>
            <c:when test="${meal.excess}">
                <tr style="color: red">
            </c:when>
            <c:otherwise>
                <tr style="color: darkgreen">
            </c:otherwise>
        </c:choose>
            <td><c:out value="${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy hh:mm')}"/></td>
            <td style><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td>Update(to be updated)</td>
            <td>Delete(to be updated)</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
