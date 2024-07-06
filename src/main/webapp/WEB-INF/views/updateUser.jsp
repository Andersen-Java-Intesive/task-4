<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update User</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
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
            margin-top: 20px;
        }
        form {
            width: 60%;
            margin: 20px auto;
            background-color: white;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        label {
            font-weight: bold;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Update User</h2>
    <form action="updateUser" method="post">
        <input type="hidden" name="id" value="${user.id}">
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" required>
        </div>
        <div class="form-group">
            <label for="secondName">Second Name:</label>
            <input type="text" class="form-control" id="secondName" name="secondName" value="${user.secondName}" required>
        </div>
        <div class="form-group">
            <label for="age">Age:</label>
            <input type="number" class="form-control" id="age" name="age" value="${user.age}" min="0" required>
        </div>
        <div class="form-group">
            <label for="team">Team:</label>
            <select class="form-control" id="team" name="team" required>
                <option value="TEAM_ONE" ${user.team == 'TEAM_ONE' ? 'selected' : ''}>TEAM_ONE</option>
                <option value="TEAM_TWO" ${user.team == 'TEAM_TWO' ? 'selected' : ''}>TEAM_TWO</option>
            </select>
        </div>
        <div class="button-container">
            <input type="submit" class="btn btn-primary" value="Commit changes">
            <a href="users" class="btn btn-secondary ml-2">Back to users list</a>
        </div>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"
        integrity="sha384-i2Dk1UqD5V2ZYYGw5kU6B9PV2ClstcBlt8qD4VAzRD1LkhdpdQ6yo4ZDQ4Ow+1Fm"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+pbzo5ml+726UlfqB5Z3Nvxca5bK41EPXmY"
        crossorigin="anonymous"></script>
</body>
</html>
