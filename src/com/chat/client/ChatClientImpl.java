package com.chat.client;
import com.chat.apis.ChatClient;
import com.chat.ui.UI;

public class ChatClientImpl implements ChatClient {
    UI uiType;

    public ChatClientImpl(UI uiType) {
        this.uiType = uiType;
    }

    public void display(String message) {
        uiType.publishMessageToUI(message);
    }

}