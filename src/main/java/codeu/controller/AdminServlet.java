package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
      if (banrate.compareTo("ban") == 0) {
        userStore.deleteUser(user);
      } else {
        Integer rate = Integer.parseInt(banrate);
        user.setRateLimit(rate);
      }
    }

    else {
      request.setAttribute("error", "Not administrator.");
      return;
    }
  }
  
}