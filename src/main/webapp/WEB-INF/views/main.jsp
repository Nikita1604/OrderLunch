<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Menu</title>
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
            <li class="active"><a href="/home"><span>Меню</span></a></li>
            <li class="menu-li"><a href="/orderhistory">История заказов</a></li>
            <sec:authorize access="hasRole('ADMIN')">
            <li class="menu-li"><a href="/deposits">Депозиты</a></li>
            <li class="menu-li"><a href="/orders">Новые заказы</a></li>
            <li class="menu-li"><a href="/userslist">Пользователи</a></li>
            </sec:authorize>
            <ul style="float:right;list-style-type:none;">
                <li class="menu-li"><a href="/logout">Выйти</a></li>
            </ul>
        </ul>
    </div>
</div>



<div class="generic-container"  style="float: left; margin-top: 120px">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="lead">Меню</span>
        </div>
        <table cellspacing="0" class="table table-hover" style="overflow:scroll">
            <%--<thead>
            &lt;%&ndash;<tr>
                &lt;%&ndash;<th>Изображение</th>&ndash;%&gt;
                &lt;%&ndash;<th>Номер</th>
                <th>Блюдо</th>
                <th>Вес</th>
                <th>Цена</th>&ndash;%&gt;
            </tr>&ndash;%&gt;
            </thead>--%>

            <tbody>
            <c:forEach items="${menu}" var="menuCategory">
                <tr style="width: 100%">
                    <th>${menuCategory.name}</th>
                </tr>
                <c:forEach items="${menuCategory.menu}" var="menuItem">
                    <%--<tr>
                        <td>
                            <img src="${menuItem.image}" style="width:125px; height:125px"/>
                        </td>
                        <td>${menuItem.in_category_id}</td>
                        <td>${menuItem.title}</td>
                        <td>${menuItem.weight}</td>
                        <td>${menuItem.cost}</td>
                    </tr>--%>
                    <tr>
                        <td valign="top">
                        <div align="top" style="float: left">
                            <div  onclick="onMenuItemClick('${menuItem.category}', ${menuItem.dish_id}, ${menuItem.in_category_id}, ${menuItem.cost})"
                                  style="float: left; display: inline-block">
                                <img src="${menuItem.image}" style="width:130px; height:130px;"/>
                            </div>
                            <div style="float: left; margin-left: 20px; width: 500px">
                                <div><h><p style="font-weight: bold">${menuItem.title}</p></h></div>
                                <div><span>${menuItem.description}</span></div>
                                <div><span>Вес: ${menuItem.weight}</span></div>
                                <div><span>Стоимость: ${menuItem.cost}</span></div>
                                <div><span>Номер: ${menuItem.in_category_id}</span></div>
                            </div>
                        </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="busket-container" style="position: fixed; right:0px; margin-top: 120px">
    <div class="panel panel-default" >
        <div class="panel-heading">
            <span class="lead">Заказ</span>
        </div>
        <table class="table table-hover" id="busket-table">
            <tbody id="busket-table-body">

            </tbody>
        </table>
        <div class="panel-footer">
            <span  style="font-size: 16px; font-weight: bold;">Цена: <span id="cost-field">0</span> руб.</span>
            <div>
                <button class="btn btn-success" onclick="acceptButtonClick()"
                        style="margin-left: auto; margin-right: auto">Оформить</button>
            </div>
        </div>
    </div>
</div>

<script>
    function onMenuItemClick(category, dish_id, in_category_id, cost) {
        var checkElem = document.getElementById(dish_id);
        if (!checkElem) {
            var element = document.getElementById("busket-table-body");
            var trElement = document.createElement("tr");
            var tdBreak = document.createElement("td");
            var tdName = document.createElement("td");
            var divName_count = document.createElement("div");
            var spanName = document.createElement("span");
            var spanCountContainer = document.createElement("span");
            var spanCount = document.createElement("span");
            var divCountContainer = document.createElement("div");
            var imageDel = document.createElement("img");
            var imageContainer = document.createElement("div");
            var costField = document.getElementById("cost-field");
            var costbefore = parseInt(costField.innerText);
            trElement.setAttribute("id", "row-" + dish_id);
            costField.innerText = costbefore + cost;
            imageDel.setAttribute("src", "/static/image/deletebutton.png");
            imageDel.setAttribute("class", "image-button");
            imageContainer.setAttribute("id", dish_id);
            imageContainer.setAttribute("class", "image-button");
            imageContainer.setAttribute("onClick",
                    "onCancelButtonClick("+dish_id+','+ cost +")");
            imageContainer.appendChild(imageDel);
            tdBreak.appendChild(imageContainer);
            console.log(tdBreak);
            console.log(category);
            console.log(in_category_id);
            spanName.innerText = category + ": номер " + in_category_id;
            spanCountContainer.innerText = "Количество: ";
            spanCount.innerText = "1";
            spanCount.setAttribute("id", "span-count-"+dish_id);
            spanCountContainer.appendChild(spanCount);
            divCountContainer.appendChild(spanCountContainer);
            divName_count.appendChild(spanName);
            divName_count.appendChild(divCountContainer);
            tdName.appendChild(divName_count);
            trElement.appendChild(tdBreak);
            trElement.appendChild(tdName);
            element.appendChild(trElement);
        } else {
            var countElem = document.getElementById("span-count-"+dish_id);
            var oldcount = parseInt(countElem.innerText) + 1;
            var costField = document.getElementById("cost-field");
            var costbefore = parseInt(costField.innerText);
            countElem.innerText = oldcount;
            costField.innerText = costbefore + cost;
        }
    }
    function onCancelButtonClick(dish_id,cost) {
        var costField = document.getElementById("cost-field");
        var costbefore = parseInt(costField.innerText);
        var countField = document.getElementById("span-count-"+dish_id);
        var count = parseInt(countField.innerHTML);
        costField.innerText = costbefore - cost*count;
        var element = document.getElementById("busket-table-body");
        element.removeChild(document.getElementById("row-" + dish_id));
    }
    function acceptButtonClick() {
        var element = document.getElementById("busket-table-body");
        var childs = element.childNodes;
        var dataPostDishes = [];
        var dataPostCounts = [];
        for (var i=1; i<childs.length; i++) {
            var dish_id = parseInt(childs[i].getAttribute("id").substring(4));
            var countElem = document.getElementById("span-count-"+dish_id);
            dataPostCounts.push(parseInt(countElem.innerText));
            dataPostDishes.push(dish_id);
            console.log(dataPostCounts);
            console.log(dataPostDishes);

        }
        $.ajax({
            type: "POST",
            url: "${home}save-order",
            data: JSON.stringify({
                dishes: dataPostDishes,
                counts: dataPostCounts
            }),
            dataType: "json",
            contentType: 'application/json',
            complete: setTimeout(clearBusket(), 100)
        });
    }
    function clearBusket() {
        /*var element = document.getElementById("busket-table-body");
        var childs = element.childNodes;
        console.log(childs);
        for (var i=0; i<childs.length; i++) {
            console.log(childs[i]);
            console.log(childs);
            element.removeChild(childs[i]);
        }*/
        window.location.reload()
    }
</script>
</body>
</html>
