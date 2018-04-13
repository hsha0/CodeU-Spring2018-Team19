package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import codeu.model.store.basic.UserStore;
import codeu.model.data.User;

public class UsersServlet extends HttpServlet {

  /**
   * Store class that gives access to Users.
   */
  private UserStore userStore;

  /**
     * This function fires when a user requests the /users URL. It simply forwards
     * the request to users.jsp.
     */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	String username = (String) request.getSession().getAttribute("user");
	if (username == null) {
      // user is not logged in, don't let them go to profile page
      response.sendRedirect("/login");
      return;
    }
	
	User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them go to profile page
      response.sendRedirect("/login");
      return;
    }
    
    request.setAttribute("user", user);
	request.getRequestDispatcher("/WEB-INF/view/users.jsp").forward(request, response);
  
  }
}