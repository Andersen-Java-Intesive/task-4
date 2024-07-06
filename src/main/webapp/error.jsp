<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Error</title>
</head>
<body>
<body>
<h1>Error</h1>
<p>
  <%
    String errorMessage = request.getParameter("error");
    if (errorMessage != null) {
      out.println(errorMessage);
    } else {
      out.println("Unknown error occurred.");
    }
  %>
<div>
  <a href="users">Back to users list</a>
</div>
</body>
</html>