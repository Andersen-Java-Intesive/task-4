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
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        table {
            width: 60%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: white;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .button-container {
            text-align: center;
            margin: 20px;
        }

        .button-container form {
            display: inline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>User pairs</h2>
    <div class="table-wrapper">
        <table class="table table-striped">
            <thead class="thead-dark">
                <thead>
                <tr>
                    <th>Team</th>
                    <th>First Name</th>
                    <th>Second Name</th>
                    <th>VS</th>
                    <th>Team</th>
                    <th>First Name</th>
                    <th>Second Name</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="pair" items="${pairs}">
                    <td>${pair.getKey().team}</td>
                    <td>${pair.getKey().firstName}</td>
                    <td>${pair.getKey().secondName}</td>
                    <td>VS</td>
                    <td>${pair.getValue().team}</td>
                    <td>${pair.getValue().firstName}</td>
                    <td>${pair.getValue().secondName}</td>
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
        <a href="users" class="btn btn-secondary ml-2">Back to users list</a>
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