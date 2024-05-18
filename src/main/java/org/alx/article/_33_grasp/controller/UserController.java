package org.alx.article._33_grasp.controller;

public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void handleUserRegistration(String username, String password) {
        try {
            // Validate user input
            if (isValidUsername(username) && isValidPassword(password)) {
                // Delegate registration to UserService
                userService.registerUser(username, password);
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    private boolean isValidUsername(String username) {
        // Validation logic for username
        return !username.isEmpty();
    }

    private boolean isValidPassword(String password) {
        // Validation logic for password
        return password.length() >= 8;
    }
}