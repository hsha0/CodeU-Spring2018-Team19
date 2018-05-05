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
    <div class = "Profile">
        <div class ="Identification">
            <p id = "Username"> ${user.getName()}</p>
            <p id = "FullName">${user.getFirstName()} ${user.getLastName()}</p>
        </div>
        <div class = "ProfilePicture">
            <img src = "${user.getPictureURL()}"/>
        </div>
        <div class = "Additional">
            <p>Age : ${user.getAge()} </p>
            <p>E-Mail : ${user.getEmail()} </p>
            <p>Phone : ${user.getPhone()}</p>
            <p>Bio : ${user.getBio()} </p>
        </div>
        <a href = "/useredit"><button>Edit</button></a>
    </div>
    <% } else{ %>
    <p>Not logged in!</p>
    <% } %>
</body>
</html>
