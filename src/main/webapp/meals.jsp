<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/functions.tld" prefix="f" %>

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
<h3><a href="${pageContext.request.contextPath}/meals?action=create">Create meal</a></h3>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr style=" color: ${meal.excess ? 'red' : 'darkgreen'}">
            <td>${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete&mealID=<c:out value="${meal.id}"/>">Delete</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=update&mealID=<c:out value="${meal.id}"/>">Update</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
