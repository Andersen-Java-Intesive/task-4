<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User pairs</title>
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
<h2>User pairs</h2>
<table>
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
        <td>${pair.first.team}</td>
        <td>${pair.first.firstName}</td>
        <td>${pair.first.secondName}</td>
        <td>VS</td>
        <td>${pair.second.team}</td>
        <td>${pair.second.firstName}</td>
        <td>${pair.second.secondName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h2>Pairless users</h2>
<table>
    <thead>
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
<div>
    <a href="users">Back to users list</a>
</div>
</body>
</html>