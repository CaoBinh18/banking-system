<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
</head>
<body>

    <h1>List Customers</h1>
    <h2>
        <a href="/customers?action=create">Create Customer</a>
    </h2>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Full Name</th>
            <th scope="col">Email</th>
            <th scope="col">Phone</th>
            <th scope="col">Address</th>
            <th scope="col">Balance</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
<%--            <jsp:useBean id="listCustomer" scope="request" type="java.util.List"/>--%>
            <c:forEach var="cus" items="${listCustomer}">
                <tr>
                    <td><c:out value="${cus.id}"/></td>
                    <td><c:out value="${cus.fullName}"/></td>
                    <td><c:out value="${cus.email}"/></td>
                    <td><c:out value="${cus.phone}"/></td>
                    <td><c:out value="${cus.address}"/></td>
                    <td><c:out value="${cus.balance}"/></td>
                    <td>
                        <a href="/customers?action=edit&id=${cus.id}">edit</a>
                        <a href="/customers?action=delete&id=${cus.id}">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>