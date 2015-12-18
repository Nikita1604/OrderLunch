<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Deposits list</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
</head>
<body>
    <div class="generic-container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span class="lead">List of Deposits </span>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>User name</th>
                        <th>Invoice</th>
                        <th>Tomorrow cost</th>
                        <th>Residue</th>
                        <th width="50"></th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach items="${deposits}" var="deposit">
                        <tr>
                            <td>1</td>
                            <td>${deposit.invoice}</td>
                            <td>${deposit.tomorrow_cost}</td>
                            <td>${deposit.residue}</td>
                            <td></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
