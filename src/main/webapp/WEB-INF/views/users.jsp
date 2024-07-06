<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users List</title>
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
            width: 80%;
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
        .action-form {
            display: inline;
            margin-right: 5px;
        }
        .action-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .action-button:hover {
            background-color: #45a049;
        }
        .edit-button {
            background-color: #008CBA;
        }
        .delete-button {
            background-color: #f44336;
        }
        .add-button {
            background-color: #4CAF50;
        }
    </style>
</head>
<body>
<h2>Users List</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Second Name</th>
        <th>Age</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.secondName}</td>
            <td>${user.age}</td>
            <td>
                <form action="updateUser" method="get" class="action-form">
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit" class="action-button edit-button">Edit</button>
                </form>
                <form action="deleteUser" method="post" class="action-form">
                    <input type="hidden" name="id" value="${user.id}">
                    <button type="submit" class="action-button delete-button">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="button-container">
    <form action="addUser" method="get">
        <button type="submit" class="action-button add-button">Add User</button>
    </form>
</div>
</body>
</html>
