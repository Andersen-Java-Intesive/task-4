<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users list</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        .container {
            margin-top: 50px;
        }
        .table-wrapper {
            margin: 20px auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">Users list</h2>
    <div class="table-wrapper">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Second Name</th>
                <th>Age</th>
                <th>Team</th>
                <th>Update</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.secondName}</td>
                    <td>${user.age}</td>
                    <td>${user.team}</td>
                    <td>
                        <form action="updateUser" method="get" style="display:inline;">
                            <input type="hidden" name="id" value="${user.id}">
                            <button type="submit" class="btn btn-primary btn-sm">Edit</button>
                        </form>
                    </td>
                    <td>
                        <form action="deleteUser" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${user.id}">
                            <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="text-center mt-3">
        <form action="addUser" method="get" style="display:inline;">
            <button type="submit" class="btn btn-success">Add User</button>
        </form>
        <form action="formPairs" method="post" style="display:inline;">
            <button type="submit" class="btn btn-warning">Form Pairs</button>
        </form>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>