package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;

public class UserServletTest {

  private UsersServlet usersServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;

  @Before
  public void setup() {
    usersServlet = new UsersServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/about.jsp")).thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoPostUpdatesUserCorrectly() throws IOException, ServletException {

    Mockito.when(mockRequest.getParameter("user")).thenReturn("newusername");
    Mockito.when(mockRequest.getParameter("pictureurl")).thenReturn("www.url.com");
    Mockito.when(mockRequest.getParameter("first")).thenReturn("firstname");
    Mockito.when(mockRequest.getParameter("last")).thenReturn("lastname");
    Mockito.when(mockRequest.getParameter("bio")).thenReturn("Example text about a bio.");
    Mockito.when(mockRequest.getParameter("age")).thenReturn("20");
    Mockito.when(mockRequest.getParameter("email")).thenReturn("example@xyz.com");
    Mockito.when(mockRequest.getParameter("phone")).thenReturn("3335551111");


    UserStore mockUserStore = Mockito.mock(UserStore.class);
    usersServlet.setUserStore(mockUserStore);
    UUID randomUUID = UUID.randomUUID();
    User user = new User(randomUUID, "newusername", "password", Instant.now());
    Mockito.when(mockUserStore.getUser("newusername")).thenReturn(user);

    usersServlet.doPost(mockRequest, mockResponse);

    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    Mockito.verify(mockUserStore).updateUser(userArgumentCaptor.capture());

    Assert.assertEquals(userArgumentCaptor.getValue().getName(), "newusername");
    Assert.assertEquals(userArgumentCaptor.getValue().getPictureURL(), "www.url.com");
    Assert.assertEquals(userArgumentCaptor.getValue().getBio(), "Example text about a bio.");
    Assert.assertTrue(userArgumentCaptor.getValue().getAge() == 20);
    Assert.assertEquals(userArgumentCaptor.getValue().getEmail(), "example@xyz.com");
    Assert.assertEquals(userArgumentCaptor.getValue().getPhoneNum(), "3335551111");

    Mockito.verify(mockResponse).sendRedirect("/user");
  }


  @Test
  public void testDoPostInvalidUser() throws IOException, ServletException {

    Mockito.when(mockRequest.getParameter("user")).thenReturn("wrongusername");
    Mockito.when(mockRequest.getParameter("pictureurl")).thenReturn("www.url.com");
    Mockito.when(mockRequest.getParameter("first")).thenReturn("firstname");
    Mockito.when(mockRequest.getParameter("last")).thenReturn("lastname");
    Mockito.when(mockRequest.getParameter("bio")).thenReturn("Example text about a bio.");
    Mockito.when(mockRequest.getParameter("age")).thenReturn("20");
    Mockito.when(mockRequest.getParameter("email")).thenReturn("example@xyz.com");
    Mockito.when(mockRequest.getParameter("phone")).thenReturn("3335551111");


    UserStore mockUserStore = Mockito.mock(UserStore.class);
    usersServlet.setUserStore(mockUserStore);
    UUID randomUUID = UUID.randomUUID();
    User user = new User(randomUUID, "newusername", "password", Instant.now());
    Mockito.when(mockUserStore.getUser("newusername")).thenReturn(user);

    usersServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "User not logged in.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    Mockito.verify(mockResponse).sendRedirect("/login");
  }

}