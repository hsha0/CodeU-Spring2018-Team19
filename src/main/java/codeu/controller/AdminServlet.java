package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import codeu.model.store.basic.UserStore;
import codeu.model.data.User;

public class AdminServlet extends HttpServlet {
 
  /**
   * Store class that gives access to Users.
   */
  private UserStore userStore;
  
  /**
   * Set up state for handling about page-related requests. This method is only
   * called when running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }
  
  /**
   * Sets the UserStore used by this servlet. This function provides a common
   * setup method for use by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }
  
  /**
   * This function fires when a user requests the /users URL. It simply forwards
   * the request to admin.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    
    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them go to admin page
      response.sendRedirect("/login");
      return;
    }
    
    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them go to admin page
      response.sendRedirect("/login");
      return;
    }
    
    if (user.isSuperUser() == false) {
      //user is not super user, don't let them go to admin page
      response.sendRedirect("/login");
    }
    
    List<User> users = userStore.getAllUsers();
    request.setAttribute("users", users);
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);  
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String banrate = request.getParameter("banrate");
    String username = request.getParameter("username");
    String adminname = (String) request.getSession().getAttribute("user");

    if (adminname == null) {
      request.setAttribute("error", "User not logged in.");
      response.sendRedirect("/login");
      return;
    }

    if (username.compareTo("") == 0) {
      request.setAttribute("error", "Invalid user.");
      response.sendRedirect("/admin");
      return;
    }

    User admin = userStore.getUser(adminname);
    User user = userStore.getUser(username);
    if (admin.isSuperUser()) {
      if (banrate == null) {
        user.ban(true);
      } else {
        Integer rate = Integer.parseInt(banrate);
        user.setRateLimit(rate);
      }
      response.sendRedirect("/admin");
    }
    else {
      request.setAttribute("error", "Not administrator.");
      return;
    }
    response.sendRedirect("/admin");
  }

  
}