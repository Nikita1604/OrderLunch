<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Menu</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
</head>
<body style="overflow-x:visible; overflow-y:visible">
<div class="generic-container">
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
                            <div style="float: left; display: inline-block">
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
</body>
</html>
