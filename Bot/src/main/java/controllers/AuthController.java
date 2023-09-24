package controllers;

import models.User;
import services.UserService;


public class AuthController {

    private final UserService userService = new UserService();

    public User login(long id) {
        return userService.findByTelegramId(id);
    }
}
