package com.revature.user.controller;

import com.revature.user.model.User;
import com.revature.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    // Test for getAllUsers (Success)
    @Test
    public void testGetAllUsers_Success() {
        List<User> users = Arrays.asList(new User(), new User());

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    // Test for getAllUsers (Failure)
    @Test
    public void testGetAllUsers_Failure() {
        when(userService.getAllUsers()).thenThrow(new RuntimeException("Error fetching users"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.getAllUsers();
        });

        assertEquals("Failed to fetch users", exception.getMessage());
    }

    // Test for getUserById (Success)
    @Test
    public void testGetUserById_Success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(userId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userId, response.getBody().getId());
        verify(userService, times(1)).getUserById(userId);
    }

    // Test for getUserById (Failure)
    @Test
    public void testGetUserById_Failure() {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenThrow(new RuntimeException("Error fetching user by id"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.getUserById(userId);
        });

        assertEquals("Failed to fetch user by id", exception.getMessage());
    }

    // Test for createUser (Success)
    @Test
    public void testCreateUser_Success() {
        User user = new User();
        user.setId(1L);

        when(userService.saveUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    // Test for createUser (Failure)
    @Test
    public void testCreateUser_Failure() {
        User user = new User();

        when(userService.saveUser(any(User.class))).thenThrow(new RuntimeException("Error creating user"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.createUser(user);
        });

        assertEquals("Failed to create user", exception.getMessage());
    }

    // Test for deleteUser (Success)
    @Test
    public void testDeleteUser_Success() {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(userId);
    }

    // Test for deleteUser (Failure)
    @Test
    public void testDeleteUser_Failure() {
        Long userId = 1L;

        doThrow(new RuntimeException("Error deleting user")).when(userService).deleteUser(userId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.deleteUser(userId);
        });

        assertEquals("Failed to delete user", exception.getMessage());
    }

    // Test for addRoleToUser (Success)
    @Test
    public void testAddRoleToUser_Success() {
        Long userId = 1L;
        User.Role role = User.Role.BUYER;

        doNothing().when(userService).addRoleToUser(userId, role);

        ResponseEntity<Void> response = userController.addRoleToUser(userId, role);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).addRoleToUser(userId, role);
    }

    // Test for addRoleToUser (Failure)
    @Test
    public void testAddRoleToUser_Failure() {
        Long userId = 1L;
        User.Role role = User.Role.BUYER;

        doThrow(new RuntimeException("Error adding role to user")).when(userService).addRoleToUser(userId, role);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userController.addRoleToUser(userId, role);
        });

        assertEquals("Failed to add role to user", exception.getMessage());
    }

    // Test for handleException (Success)
    @Test
    public void testHandleException_Success() {
        Exception exception = new Exception("Some error occurred");

        ResponseEntity<String> response = userController.handleException(exception);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An error occurred: Some error occurred", response.getBody());
    }
}
