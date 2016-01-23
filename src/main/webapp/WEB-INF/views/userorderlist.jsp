<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Menu</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
</head>
<body style="overflow-x:visible; overflow-y:visible">
<div class="generic-container"  style="float: left">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="lead">История заказов</span>
        </div>
        <table cellspacing="0" class="table table-hover" style="overflow:scroll">
            <thead>
            <tr>
                <th>Дата</th>
                <th>Список блюд</th>
                <th>Итоговая стоимость</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr style="width: 100%">
                    <td>
                        <span>${order.date}</span>
                    </td>
                    <td valign="top">
                        <ul>
                            <c:forEach items="${order.orderList.orderItems}" var="orderItem">
                            <li>
                                <div>
                                    <span>${orderItem.dishName}</span>
                                    <div>
                                        <span>Количество: ${orderItem.count}</span>
                                    </div>
                                </div>
                            </li>
                            </c:forEach>
                        </ul>

                    </td>

                    <td>
                        <span>${order.orderList.cost}</span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
