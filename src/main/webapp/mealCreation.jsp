<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: godsa
  Date: 12.10.2021
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Meal</title>
    <style>
        div.settings {
            padding-top: 20px;
            padding-bottom: 20px;
            display: grid;
            grid-template-columns: max-content max-content;
            grid-gap: 30px;
        }
        div.settings label {text-align: left;}
        div.settings label:after {content: ":";}
    </style>
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
<form method="post" action="${pageContext.request.contextPath}/meals?action=default">
    <div class="settings">
        <input type="number" name="id" hidden="hidden" value="<c:out value="${meal.id}"/>">

        <label>DateTime</label>
        <input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/>">

        <label>Description</label>
        <input type="text" name="desc" value="<c:out value="${meal.description}"/>">

        <label>Calories</label>
        <input type="number" name="cal" value="<c:out value="${meal.calories}"/>">
    </div>
    <input type="submit" value="Save">
</form>
    <button onclick="goBack()">Cancel</button>
</body>
</html>
