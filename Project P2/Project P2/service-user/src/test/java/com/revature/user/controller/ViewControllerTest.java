package com.revature.user.controller;

import com.revature.user.model.User;
import com.revature.user.model.User.Role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ViewControllerTest {

    @InjectMocks
    private ViewController viewController;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpSession session;

    // Test for home (Success)
    @Test
    public void testHome_Success() {
        String viewName = viewController.home();
        assertEquals("home", viewName);
    }

    // Test for loginPage (Success)
    @Test
    public void testLoginPage_Success() {
        String viewName = viewController.loginPage();
        assertEquals("login", viewName);
    }

    // Test for registrationPage (Success)
    @Test
    public void testRegistrationPage_Success() {
        String viewName = viewController.registrationPage();
        assertEquals("registration", viewName);
    }

    // Test for doLogin (Failure)
    @Test
    public void testDoLogin_Failure() {
        String email = "test@example.com";
        String password = "password";

        when(restTemplate.getForObject(anyString(), eq(User[].class))).thenReturn(null);

        ModelAndView modelAndView = viewController.doLogin(email, password);

        assertEquals("login", modelAndView.getViewName());
        assertEquals("Invalid email or password.", modelAndView.getModel().get("error"));
    }

    // Test for logout (Success)
    @Test
    public void testLogout_Success() {
        ModelAndView modelAndView = viewController.logout(session);

        assertEquals("redirect:http://localhost:8080/", modelAndView.getViewName());
        verify(session, times(1)).invalidate();
    }

    // Test for logout (Failure)
    @Test
    public void testLogout_Failure() {
        doThrow(new RuntimeException("Failed to logout")).when(session).invalidate();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            viewController.logout(session);
        });

        assertEquals("Failed to process logout request.", exception.getMessage());
    }

    // Test for adminPage (Success)
    @Test
    public void testAdminPage_Success() {
        String viewName = viewController.adminPage();
        assertEquals("adminPage", viewName);
    }

    // Test for sellerPage (Success)
    @Test
    public void testSellerPage_Success() {
        String viewName = viewController.sellerPage();
        assertEquals("sellerPage", viewName);
    }

    // Test for handleException (Success)
    @Test
    public void testHandleException_Success() {
        Exception exception = new Exception("Some error occurred");

        ResponseEntity<String> response = viewController.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Some error occurred", response.getBody());
    }
}
