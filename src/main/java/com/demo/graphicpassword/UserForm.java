package com.demo.graphicpassword;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserForm extends JFrame{
    public JPanel rootPanel;
    public JTextArea personalNoteFiled;
    public JLabel loginField;
    public JButton exitButton;
    public JButton saveButton;

    private User user;

    UserForm(ApplicationInterface applicationInterface){
        setContentPane(rootPanel);
        setSize(300, 300);
        setLocation(300, 300);
        setTitle("Пользователь");
        setAlwaysOnTop(true);

        saveButton.addActionListener(e -> user.setNote(personalNoteFiled.getText()));
        exitButton.addActionListener(e -> applicationInterface.exit());
    }

    void setUser(User user){
        this.user = user;
        loginField.setText(user.getLogin());
        personalNoteFiled.setText(user.getNote());
    }
}
