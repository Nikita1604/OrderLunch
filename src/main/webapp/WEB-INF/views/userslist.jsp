<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
</head>

<body>
<div class="panel panel-default" style="position: fixed; width: 100%">
    <div class="panel-heading" style="position: fixed; width: 100%">
        <span class="lead">OrderLunch</span>
    </div>
    <div class="menu-bar" style="position: fixed; margin-top: 50px;">
        <ul class="menu-container">
            <li class="menu-li"><a href="/home"><span>Меню</span></a></li>
            <li class="menu-li"><a href="/orderhistory">История заказов</a></li>
            <sec:authorize access="hasRole('ADMIN')">
                <li class="menu-li"><a href="/deposits">Депозиты</a></li>
                <li class="menu-li"><a href="/orders">Новые заказы</a></li>
                <li class="active"><a href="/userslist">Пользователи</a></li>
            </sec:authorize>
            <ul style="float:right;list-style-type:none;">
                <li class="menu-li"><a href="/logout">Выйти</a></li>
            </ul>
        </ul>
    </div>
</div>
<div class="generic-container" style="float: left; margin-top: 120px">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Users </span></div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Login</th>
                <th width="100"></th>
                <th width="100"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.e_mail}</td>
                    <td>${user.login}</td>
                    <td><a href="<c:url value='/edit-user-${user.login}' />" class="btn btn-success

custom-width">edit</a></td>
                    <td><a href="<c:url value='/delete-user-${user.login}' />" class="btn btn-danger

custom-width">delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="well">
        <a href="<c:url value='/newuser' />">Add New User</a>
    </div>
</div>
</body>
</html>