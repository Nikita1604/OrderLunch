<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Deposits list</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
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
                                <div onclick="onPlusClick(${depos.deposit.id})" style="width: 25px">
                                    <img src="/static/image/plusimage.png" style="width: 25px; height: 25px"/>
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
            var input = document.createElement("input");

            input.setAttribute("id", "value-input");
            var button = document.createElement("button");
            button.innerText = "Save";
            button.setAttribute("onclick", "saveValue("+id+")");
            tdInput.appendChild(input);
            tdButton.appendChild(button);
            element.appendChild(tdInput);
            element.appendChild(tdButton);
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
