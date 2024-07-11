<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        .pink-team {
            background-color: pink;
        }
        .orange-team {
            background-color: orange;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">Users list</h2>
    <div class="table-wrapper">
        <form action="addMarks" method="post">
            <div class="form-group">
                <label for="lessonDate">Select Lesson Date:</label>
                <input type="date" id="lessonDate" name="lessonDate" class="form-control" required>
            </div>
            <h2 class="text-center">User pairs</h2>
            <div class="table-wrapper">
                <table class="table table-striped">
                    <thead class="thead-dark">
                    <tr>
                        <th>Team</th>
                        <th>First Name</th>
                        <th>Second Name</th>
                        <th>User Mark</th>
                        <th>VS</th>
                        <th>User Mark</th>
                        <th>Team</th>
                        <th>First Name</th>
                        <th>Second Name</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="pair" items="${pairs}">
                        <tr>
                            <td class="${pair.key.team == 'pink' ? 'pink-team' : (pair.key.team == 'orange' ? 'orange-team' : '')}">${pair.key.team}</td>
                            <td class="${pair.key.team == 'pink' ? 'pink-team' : (pair.key.team == 'orange' ? 'orange-team' : '')}">${pair.key.firstName}</td>
                            <td class="${pair.key.team == 'pink' ? 'pink-team' : (pair.key.team == 'orange' ? 'orange-team' : '')}">${pair.key.secondName}</td>
                            <td><input type="number" name="userMark_${pair.key.id}_${pair.value.id}" class="form-control" step="0.5" min="0" max="6" required></td>
                            <td>VS</td>
                            <td><input type="number" name="pairedUserMark_${pair.key.id}_${pair.value.id}" class="form-control" step="0.5" min="0" max="6" required></td>
                            <td class="${pair.value.team == 'pink' ? 'pink-team' : (pair.value.team == 'orange' ? 'orange-team' : '')}">${pair.value.team}</td>
                            <td class="${pair.value.team == 'pink' ? 'pink-team' : (pair.value.team == 'orange' ? 'orange-team' : '')}">${pair.value.firstName}</td>
                            <td class="${pair.value.team == 'pink' ? 'pink-team' : (pair.value.team == 'orange' ? 'orange-team' : '')}">${pair.value.secondName}</td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="text-center mt-3">
                <button type="submit" class="btn btn-success">Save Marks</button>
            </div>
        </form>
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
                        <td class="${user.team == 'pink' ? 'pink-team' : (user.team == 'orange' ? 'orange-team' : '')}">${user.id}</td>
                        <td class="${user.team == 'pink' ? 'pink-team' : (user.team == 'orange' ? 'orange-team' : '')}">${user.firstName}</td>
                        <td class="${user.team == 'pink' ? 'pink-team' : (user.team == 'orange' ? 'orange-team' : '')}">${user.secondName}</td>
                        <td class="${user.team == 'pink' ? 'pink-team' : (user.team == 'orange' ? 'orange-team' : '')}">${user.age}</td>
                        <td class="${user.team == 'pink' ? 'pink-team' : (user.team == 'orange' ? 'orange-team' : '')}">${user.team}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    <div class="text-center mt-3">
            <div class="button-container">
                <a href="users" class="btn btn-secondary ml-2">Back to users list</a>
            </div>
        <form action="formPairs" method="post" style="display:inline;">
            <button type="submit" class="btn btn-warning">Form Pairs</button>
        </form>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>