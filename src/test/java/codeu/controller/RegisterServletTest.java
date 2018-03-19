package codeu.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RegisterServletTest {

    private RegisterServlet registerServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() {
	registerServlet = new RegisterServlet();
	mockRequest = Mockito.mock(HttpServletRequest.class);
	mockResponse = Mockito.mock(HttpServletResponse.class);
	mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
	Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/register.jsp")).thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
	registerServlet.doGet(mockRequest, mockResponse);

	Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
    
    @Test
    public void testDoPost_BasUsername() throws IOException, ServletException {
	Mockito.when(mockRequest.getParameter("username")).thenReturn("bad !@#$% username");
	
	registerServlet.doPost(mockRequest, mockResponse);
	
	Mockito.verify(mockRequest)
        .setAttribute("error", "Please enter only letters, numbers, and spaces.");
	Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
}