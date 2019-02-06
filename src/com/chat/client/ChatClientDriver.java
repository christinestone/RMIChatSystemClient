package com.chat.client;

import java.io.IOException;
import java.util.Scanner;

import com.chat.ui.GUI;

public class ChatClientDriver {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception, IOException {
		CommunicationHelper helper = new CommunicationHelper();
//		System.out.println("Select from below:");
//		System.out.println("1. Console");
//		System.out.println("2. GUI");
//		if(sc.nextInt() == 1) {
//			new TextUI(helper);		
//		}else
			new GUI(helper);
	}

}
