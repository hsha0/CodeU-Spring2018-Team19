<nav>
  <a id="navTitle" href="/">Team TNT!</a>
  <a href="/conversations">Conversations</a>
  <% if(request.getSession().getAttribute("user") != null){ %>
    <a href="/user">Hello <%= request.getSession().getAttribute("user") %>!</a>
  <% } else{ %>
    <a href="/login">Login</a>
    <a href="/register">Register</a>
  <% } %>
  <a href="/about.jsp">About</a>
  <a href="/testdata">Load Test Data</a>
</nav>
