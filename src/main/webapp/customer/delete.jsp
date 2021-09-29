<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Delete customer</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>
<h1>Delete customer</h1>
<p>
    <a href="/customers">Back list customer</a>
</p>
<div class="container">
    <form class="needs-validation" novalidate method="post">
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="validationCustom01">Full Name</label>
                <input type="text" class="form-control" name="fullName" id="validationCustom01" value="${customer.fullName}" readonly>
            </div>
            <div class="col-md-6 mb-3">
                <label for="validationCustom02">Email</label>
                <input type="text" class="form-control" name="email" id="validationCustom02" value="${customer.email}" readonly>
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="validationCustom03">Phone</label>
                <input type="text" class="form-control" name="phone" id="validationCustom03" value="${customer.phone}" readonly>
            </div>
            <div class="col-md-6 mb-3">
                <label for="validationCustom04">Address</label>
                <input type="text" class="form-control" name="address" id="validationCustom04" value="${customer.address}" readonly>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Delete</button>
    </form>
</div>
</body>
</html>
