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

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String username = (String) request.getSession().getAttribute("user");
    String pictureURL = (String) request.getSession().getAttribute("pictureurl");
    String bio = (String) request.getSession().getAttribute("bio");
    Integer age = (Integer) request.getSession().getAttribute("age");
    String email = (String) request.getSession().getAttribute("email");
    String phoneNum = (String) request.getSession().getAttribute("phone");

    User user = userStore.getUser(username);
    if(username == null) {
      response.sendRedirect("/login");
      return;
    }
    User.Builder userBuilder = new User.Builder(user.getId(),user.getName(), user.getPassword(), user.getCreationTime());
    userBuilder.setAge(age);
    userBuilder.setFirstName(user.getFirstName());
    userBuilder.setLastName(user.getLastName());
    userBuilder.setEmail(email);
    userBuilder.setPhoneNum(phoneNum);
    userBuilder.setBio(bio);
    userBuilder.setPictureURL(pictureURL);
    user = userBuilder.createUser();
    userStore.updateUser(user);
  }
}