package com.chat.client;

import java.io.IOException;
import java.util.Scanner;

import com.chat.ui.GUI;
import com.chat.ui.TextUI;

public class StartChatClient {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception, IOException {
		CommunicationHelper helper = new CommunicationHelper();
		new GUI(helper);
	}

}
