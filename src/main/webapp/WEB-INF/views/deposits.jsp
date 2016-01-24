<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Deposits list</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
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
                <li class="active"><a href="/deposits">Депозиты</a></li>
                <li class="menu-li"><a href="/orders">Новые заказы</a></li>
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
                <span class="lead">Список депозитов</span>
            </div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Пользователь</th>
                        <th>Счёт</th>
                        <th>Стоимость заказа</th>
                        <th>Остаток</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach items="${deposits}" var="depos">
                        <tr id="deposit-${depos.deposit.id}">
                            <td>${depos.user.name}</td>
                            <td>${depos.deposit.invoice}</td>
                            <td>${depos.deposit.tomorrow_cost}</td>
                            <td>${depos.deposit.residue}</td>
                            <td style="width: 40px">
                                <div id="plus-button-container" onclick="onPlusClick(${depos.deposit.id})" style="width: 25px">
                                    <img id="plus-button-img" src="/static/image/plus.png" style="width: 25px; height: 25px"/>
                                </div>

                            </td>

                        </tr>
                    </c:forEach>
                    <div id="form_container"></div>
                </tbody>
            </table>
        </div>
    </div>
    <script>
        function onPlusClick(id) {
            var element = document.getElementById("deposit-"+id);
            var tdInput = document.createElement("td");
            var tdButton = document.createElement("td");
            tdInput.setAttribute("id", "input-td");
            tdButton.setAttribute("id", "button-td");
            var input = document.createElement("input");
            var plusButton = document.getElementById("plus-button-img");
            plusButton.setAttribute("src", "/static/image/delete.png");
            var plusButtonContainer = document.getElementById("plus-button-container");
            plusButtonContainer.setAttribute("onClick", "discardChanges("+id+")");
            input.setAttribute("id", "value-input");
            var button = document.createElement("button");
            button.innerText = "Save";
            button.setAttribute("onclick", "saveValue("+id+")");
            tdInput.appendChild(input);
            tdButton.appendChild(button);
            element.appendChild(tdInput);
            element.appendChild(tdButton);
        }
        function discardChanges(id) {
            var element = document.getElementById("deposit-"+id);
            element.removeChild(document.getElementById("input-td"));
            element.removeChild(document.getElementById("button-td"));
            var plusButton = document.getElementById("plus-button-img");
            var plusButtonContainer = document.getElementById("plus-button-container");
            plusButton.setAttribute("src", "/static/image/plus.png");
            plusButtonContainer.setAttribute("onClick", "onPlusClick("+id+")");
        }
        function saveValue(id) {
            var valueInput = document.getElementById("value-input");
            var value = parseInt(valueInput.value);
            /*$.ajax({
                        type: "GET",
                        url: "/update-deposit-"+id,
                        data: ({value: value}),
                        complete: setTimeout(redirect(), 400)
                    });*/
            $.ajax({
                type: "POST",
                url: "${home}update-deposit-"+parseInt(id),
                data: "value=" + value,
                complete: setTimeout(redirect(), 400)
            });
        }
        function redirect() {
            $.ajax({
                method: "GET",
                url: "/deposits",
                complete: setTimeout(update, 400)
            });
        }
        function update() {
            window.location.reload(true)
        }
    </script>
</body>
</html>
