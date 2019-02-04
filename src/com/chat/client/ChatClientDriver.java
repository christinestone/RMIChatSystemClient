package com.chat.client;

import java.io.IOException;

import com.chat.ui.TextUI;

public class ChatClientDriver {

    public static void main(String[] args) throws Exception, IOException {

        CommunicationHelper ch = new CommunicationHelper();
        new TextUI(ch);
    }

}
