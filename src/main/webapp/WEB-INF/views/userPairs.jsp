<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User pairs</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <style>
        body {
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-top: 20px;
        }
        .table-wrapper {
            width: 80%;
            margin: 20px auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .button-container {
            text-align: center;
            margin: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>User pairs</h2>
    <div class="table-wrapper">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>Team</th>
                <th>First Name</th>
                <th>Second Name</th>
                <th class="text-center">VS</th>
                <th>Team</th>
                <th>First Name</th>
                <th>Second Name</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="pair" items="${pairs}">
                <tr>
                    <td>${pair.first.team}</td>
                    <td>${pair.first.firstName}</td>
                    <td>${pair.first.secondName}</td>
                    <td class="text-center">VS</td>
                    <td>${pair.second.team}</td>
                    <td>${pair.second.firstName}</td>
                    <td>${pair.second.secondName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <h2>Pairless users</h2>
    <div class="table-wrapper">
        <table class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Second Name</th>
                <th>Age</th>
                <th>Team</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${pairlessUsers}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.secondName}</td>
                    <td>${user.age}</td>
                    <td>${user.team}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="button-container">
        <a href="users" class="btn btn-secondary">Back to users list</a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"
        integrity="sha384-i2Dk1UqD5V2ZYYGw5kU6B9PV2ClstcBlt8qD4VAzRD1LkhdpdQ6yo4ZDQ4Ow+1Fm"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+pbzo5ml+726UlfqB5Z3Nvxca5bK41EPXmY"
        crossorigin="anonymous"></script>
</body>
</html>
