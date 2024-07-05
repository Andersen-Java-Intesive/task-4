<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update user</title>
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
        <input type="submit" value="Commit changes">
    </div>
</form>
<div>
    <a href="users">Back to users list</a>
</div>
</body>
</html>