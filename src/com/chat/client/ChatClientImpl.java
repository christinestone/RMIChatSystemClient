package com.chat.client;
import com.chat.apis.ChatClient;
import com.chat.ui.UI;

public class ChatClientImpl implements ChatClient {
    UI uiType;
    boolean isOnline;

    public ChatClientImpl(UI uiType) {
        this.uiType = uiType;
        isOnline = true;
    }

    public void receive(String message) {
        uiType.displayMessage(message);
    }

}
