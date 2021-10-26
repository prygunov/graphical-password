package com.demo.graphicpassword;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application implements ApplicationInterface {

    private final LoginForm loginForm; // структура окна входа
    private final UserForm userForm;
    private List<User> userList = new ArrayList<>(); // список пользователей

    Application(){
        loginForm = new LoginForm(this);
        userForm = new UserForm(this);
        loginForm.setVisible(true);
    }

    @Override
    public void registerUser(User user) {
        // регистрация пользователя
        userList.add(user);
        login(user.getLogin(), user.getPassword());
    }

    @Override
    public void login(String login, List<Integer> password) {
        // вход пользователя
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                userForm.setUser(user);
                userForm.setVisible(true);
                loginForm.setVisible(false);
                return;
            }
        }
        JOptionPane.showMessageDialog(loginForm, "Логин или пароль некорректны.");
    }

    @Override
    public void exit() {
        // выход пользователя из его окна
        userForm.setVisible(false);
        loginForm.setVisible(true);
    }
}
