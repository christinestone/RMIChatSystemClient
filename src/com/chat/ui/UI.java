package com.chat.ui;

public interface UI {

    void getInputFromUI(String name);

    UI getUIType();

    String getUsername();

    void publishMessageToUI(String message);

}
