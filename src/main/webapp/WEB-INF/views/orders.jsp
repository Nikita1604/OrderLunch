<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>OrderList</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
</head>
<body style="overflow-x:visible; overflow-y:visible">

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
                <li class="active"><a href="/orders">Новые заказы</a></li>
                <li class="menu-li"><a href="/userslist">Пользователи</a></li>
            </sec:authorize>
            <ul style="float:right;list-style-type:none;">
                <li class="menu-li"><a href="/logout">Выйти</a></li>
            </ul>
        </ul>
    </div>
</div>
<div class="generic-container" style="float: left; margin-top: 120px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="lead">Неоформленные заказы</span>
        </div>
        <table cellspacing="0" class="table table-hover" style="overflow:scroll">
            <thead>
            <tr>
                <th>Имя</th>
                <th>Список блюд</th>
                <th>Итоговая стоимость</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr style="width: 100%">
                    <td>
                        <span>${order.user.name}</span>
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


<div class="generic-container">
    <div class="well lead">Contacts</div>
    <form:form method="POST" modelAttribute="manager" class="form-horizontal">

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="name">Name</label>
                <div class="col-md-7">
                    <form:input type="text" path="name" id="name" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="name" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="company">Company</label>
                <div class="col-md-7">
                    <form:input type="text" path="company" id="company" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="company" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="phone">Phone</label>
                <div class="col-md-7">
                    <form:input type="text" path="phone" id="phone" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="phone" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="address">Address</label>
                <div class="col-md-7">
                    <form:input type="text" path="address" id="address" class="form-control input-sm" />
                    <div class="has-error">
                        <form:errors path="address" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="email">Email</label>
                <div class="col-md-7">
                    <form:input type="text" path="email" id="email" class="form-control input-sm" />
                    <div class="has-error">
                        <form:errors path="email" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-actions floatRight">
                <input type="submit" value="Accept" class="btn btn-primary btn-sm"/>
            </div>
        </div>
    </form:form>
</div>
</div>
</body>
</html>
