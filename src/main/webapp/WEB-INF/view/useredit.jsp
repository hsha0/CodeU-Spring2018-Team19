<%--
  Created by IntelliJ IDEA.
  User: jadzeineddine
  Date: 4/29/18
  Time: 7:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/user.css">
</head>
<body>
<%@ include file = "navbar.jsp" %>
<% if(request.getSession().getAttribute("user") != null){ %>
<form action="/useredit" method="POST">
    <div class = "Profile">
        <div class ="Identification">
            <p id = "FullName">First Last : </p> <input type="text" name="first" id="first"> <input type="text" name="last" id="last">
        </div>
        <div class = "ProfilePicture">
            <p>Profile Pic URL:</p> <input type="text" name="pictureurl" id="pictureurl">
        </div>
        <div class = "Additional">
            <p>Age : </p> <input type = "number" name = "age" id = "age">
            <p>E-Mail : </p> <input type = "text" name = "email" id = "email">
            <p>Phone : </p> <input type = "number" name = "phone" id = "phone">
            <p>Bio : </p> <input type = "text" name = "bio" id = "bio">
        </div>
        <button type="submit" style = "margin-left: 45%; margin-bottom: 10px">Submit</button>
    </div>
</form>
<% } else{ %>
<p>Not logged in!</p>
<% } %>
</body>
</html>
