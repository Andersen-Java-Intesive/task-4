<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список пользователей</title>
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
<h2>Users list</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Second Name</th>
        <th>Age</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.secondName}</td>
        <td>${user.age}</td>
        <td>
            <form action="updateUser" method="get" style="display:inline;">
                <input type="hidden" name="id" value="${user.id}">
                <input type="submit" value="Edit">
            </form>
        </td>
        <td>
            <form action="deleteUser" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${user.id}">
                <input type="submit" value="Remove">
            </form>
        </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="button-container">
    <form action="addUser" method="get">
        <input type="submit" value="Add User">
    </form>
</div>
</body>
</html>