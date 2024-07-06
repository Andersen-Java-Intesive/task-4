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
<h2>Add user</h2>
<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>
<form action="addUser" method="post">
    <div>
        <label for="firstName">Name:</label>
        <input type="text" id="firstName" name="firstName" required>
    </div>
    <div>
        <label for="secondName">Second Name:</label>
        <input type="text" id="secondName" name="secondName" required>
    </div>
    <div>
        <label for="age">Age:</label>
        <input type="number" id="age" name="age" min="0" required>
    </div>
    <div>
        <label for="team">Team:</label>
        <select id="team" name="team" required>
            <option value="TEAM_ONE">TEAM_ONE</option>
            <option value="TEAM_TWO">TEAM_TWO</option>
        </select>
    </div>
    <div>
        <input type="submit" value="Add">
    </div>
    <div>
        <a href="users">Back to users list</a>
    </div>
</form>

</body>
</html>