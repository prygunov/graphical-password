package com.demo.graphicpassword;

import java.util.List;

// простая реализация пользователя
public class User {

    private final String login;
    private final List<Integer> password;
    private String note;

    public User(String login, List<Integer> password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public List<Integer> getPassword() {
        return password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password=" + password +
                ", note='" + note + '\'' +
                '}';
    }
}
