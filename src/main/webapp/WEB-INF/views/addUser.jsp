<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add user</title>
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
        <input type="submit" value="Add">
    </div>
</form>
<div>
    <a href="users">Back to users list</a>
</div>
</body>
</html>