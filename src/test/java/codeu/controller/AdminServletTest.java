package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;

public class AdminServletTest {
  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;
  
  @Before
  public void setup() {
    adminServlet = new AdminServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
            .thenReturn(mockRequestDispatcher);

    mockUserStore = Mockito.mock(UserStore.class); 
    adminServlet.setUserStore(mockUserStore);
  }
  
  @Test
  public void testDoGet() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "username", "password", Instant.now());
    User.Builder userBuilder = new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime());
    userBuilder.setSuperUser(true);
    user = userBuilder.createUser();
    
    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("username");
    Mockito.when(mockUserStore.getUser("username")).thenReturn(user);
    List<User> fakeUsers = new ArrayList<>();
    fakeUsers.add(user);     
    Mockito.when(mockUserStore.getAllUsers()).thenReturn(fakeUsers);
    
    adminServlet.doGet(mockRequest, mockResponse);
    
    Mockito.verify(mockRequest).setAttribute("users", fakeUsers);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
  
  @Test
  public void testDoGetInvalidUsername() throws IOException, ServletException {
    
    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn(null);
    
    adminServlet.doGet(mockRequest, mockResponse);
    
    Mockito.verify(mockResponse).sendRedirect("/login");
  }
  
  @Test
  public void testDoGetNonexistingUser() throws IOException, ServletException {
    
    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("username");
    Mockito.when(mockUserStore.getUser("username")).thenReturn(null);
    
    adminServlet.doGet(mockRequest, mockResponse);
    
    Mockito.verify(mockResponse).sendRedirect("/login");
    
  }
  
  @Test
  public void testDoGetNotSuperUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "username", "password", Instant.now());
    
    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("username");
    Mockito.when(mockUserStore.getUser("username")).thenReturn(user);
    List<User> fakeUsers = new ArrayList<>();
    fakeUsers.add(user);     
    Mockito.when(mockUserStore.getAllUsers()).thenReturn(fakeUsers);
    
    adminServlet.doGet(mockRequest, mockResponse);
    
    Mockito.verify(mockResponse).sendRedirect("/login");
  }

  @Test
  public void testBanUserSuccessful() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "username", "password", Instant.now());
    User.Builder userBuilder = new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime());
    user = userBuilder.createUser();

    User admin = new User(UUID.randomUUID(), "admin", "password", Instant.now());
    User.Builder adminBuilder= new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime()).setSuperUser(true);
    admin = adminBuilder.createUser();

    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("admin");
    Mockito.when(mockRequest.getParameter("username")).thenReturn("username");

    Mockito.when(mockUserStore.getUser("admin")).thenReturn(admin);
    Mockito.when(mockUserStore.getUser("username")).thenReturn(user);

    adminServlet.doPost(mockRequest, mockResponse);

    Assert.assertTrue(user.isBanned());
  }

  @Test
  public void testRateUserSucessful() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "username", "password", Instant.now());
    User.Builder userBuilder = new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime());
    user = userBuilder.createUser();

    User admin = new User(UUID.randomUUID(), "admin", "password", Instant.now());
    User.Builder adminBuilder= new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime()).setSuperUser(true);
    admin = adminBuilder.createUser();

    Mockito.when(mockRequest.getParameter("banrate")).thenReturn("5");
    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("admin");
    Mockito.when(mockRequest.getParameter("username")).thenReturn("username");

    Mockito.when(mockUserStore.getUser("admin")).thenReturn(admin);
    Mockito.when(mockUserStore.getUser("username")).thenReturn(user);

    adminServlet.doPost(mockRequest, mockResponse);

    Assert.assertTrue(user.getRateLimit() == 5);

  }

  @Test
  public void testUserNotAdmin() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "username", "password", Instant.now());
    User.Builder userBuilder = new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime());
    user = userBuilder.createUser();

    //Not a super user
    User admin = new User(UUID.randomUUID(), "admin", "password", Instant.now());
    User.Builder adminBuilder= new User.Builder(user.getId(), user.getName(), user.getPassword(), user.getCreationTime());
    admin = adminBuilder.createUser();

    Mockito.when(mockRequest.getSession().getAttribute("user")).thenReturn("admin");
    Mockito.when(mockRequest.getParameter("username")).thenReturn("username");

    Mockito.when(mockUserStore.getUser("admin")).thenReturn(admin);
    Mockito.when(mockUserStore.getUser("username")).thenReturn(user);

    adminServlet.doPost(mockRequest, mockResponse);

    Assert.assertTrue(!user.isBanned());

  }
  
}