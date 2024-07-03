<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список пользователей</title>
    <style>
        table {
            width: 50%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid black;
        }

        th, td {
            padding: 10px;
            text-align: left;
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