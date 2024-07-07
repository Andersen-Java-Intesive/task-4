<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users list</title>
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
        form {
            width: 60%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: white;
        }
        form, th, td {
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
<h2>Update User</h2>
<form action="updateUser" method="post">
    <input type="hidden" name="id" value="${user.id}">
    <div>
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
    </div>
    <div>
        <label for="secondName">Second Name:</label>
        <input type="text" id="secondName" name="secondName" value="${user.secondName}" required>
    </div>
    <div>
        <label for="age">Age:</label>
        <input type="number" id="age" name="age" value="${user.age}" min="0" required>
    </div>
    <div>
        <label for="team">Team:</label>
        <select id="team" name="team" required>
            <option value="ORANGE_TEAM" ${user.team == 'ORANGE_TEAM' ? 'selected' : ''}>ORANGE_TEAM</option>
            <option value="PINK_TEAM" ${user.team == 'PINK_TEAM' ? 'selected' : ''}>PINK_TEAM</option>
        </select>
    </div>
    <div>
        <input type="submit" value="Commit changes">
    </div>
    <div>
        <a href="users">Back to users list</a>
    </div>
</form>

</body>
</html>