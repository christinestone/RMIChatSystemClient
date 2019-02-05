package com.chat.client;

import java.io.IOException;
import java.util.Scanner;

import com.chat.ui.GUI;
import com.chat.ui.TextUI;

public class ChatClientDriver {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception, IOException {
		CommunicationHelper helper = new CommunicationHelper();
		System.out.println("Select from below:");
		System.out.println("1. GUI\n2. Console");
		if(sc.nextInt() == 1) {
			new GUI(helper);
		}else
			new TextUI(helper);
	}

}
