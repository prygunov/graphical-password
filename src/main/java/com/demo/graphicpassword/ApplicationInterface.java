package com.demo.graphicpassword;

import java.util.List;
import java.util.Map;

public interface ApplicationInterface {

    void registerUser(User user);
    void login(String login, List<Integer> password);
    void exit();

}
