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
            <p id = "Username"> ${sessionScope.user}</p>
            <p id = "FullName">${sessionScope.first} ${sessionScope.last}</p>
        </div>
        <div class = "ProfilePicture">
            <img src = "${sessionScope.pictureurl}"/>
        </div>
        <div class = "Additional">
            <p>Age : ${sessionScope.age} </p>
            <p>E-Mail : ${sessionScope.email} </p>
            <p>Phone : ${sessionScope.phone}</p>
            <p>Bio : ${sessionScope.bio} </p>
        </div>
        <a href = "useredit.jsp"><button>Edit</button></a>
    </div>
    <% } else{ %>
    <p>Not logged in!</p>
    <% } %>
</body>
</html>
