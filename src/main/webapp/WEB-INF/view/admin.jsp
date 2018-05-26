<%@ page import="codeu.model.data.User" %>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.store.basic.UserStore" %><%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>

<%
   List<User> users = (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/css/main.css" type="text/css">

    <%--<style>--%>
        <%--#chat {--%>
            <%--background-color: white;--%>
            <%--height: 500px;--%>
            <%--overflow-y: scroll--%>
        <%--}--%>
    <%--</style>--%>

</head>
<body >
<%@ include file = "navbar.jsp" %>

<div id="container">

    <h1>Users<h1/>

    <div id="users">
        <ul>
            <%
                for (User user : users) {
                    String author = user.getName();
                    String pic = user.getPictureURL();
            %>
            <li class = "user">
                <img src = "<%= pic %>" class = "avatar"/>
                <strong><a href = "/user?user=<%= author %>"><%= author %></a>:
                    Rate Limit:  </strong> <form action = "/admin?username=<%= author %>" method = "POST"><input type = "text" name = "banrate" id = "banrate" value = "<%=user.getRateLimit()%>"> </form>
                    <a href = "#" onclick = "ban('${author}')">Ban</a>
                </strong>
            </li>
            <%
                }
            %>
        </ul>
    </div>
</div>

</body>
<script>
    function ban(username){
        var url = "http://gcu-tnt.appspot.com/admin?username="+username;
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.readyState != 4 || xhr.status != "200") {
                console.error("Error deleting conversation");
            }
        }
        xhr.open('POST', url, true);
        xhr.send(null);
    }
</script>
</html>
