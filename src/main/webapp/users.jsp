<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>You are authorized as:</h3>
<ul style="font-size: medium">
    <c
        <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User" scope="request"/>
        <li><a href="meals?action=all">${user.email}</a></li>
</ul>
</body>
</html>