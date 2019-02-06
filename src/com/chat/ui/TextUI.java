package com.chat.ui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import com.chat.client.CommunicationHelper;

public class TextUI implements UI {
	Scanner sc = new Scanner(System.in);
	CommunicationHelper helper = new CommunicationHelper();
	String message, name;

	public TextUI(CommunicationHelper helper) {
		this.helper = helper;
		helper.rmiSetup(this);
		getInput();
	}

	@Override
	public void displayMessage(String input) {
		System.out.println(input);
	}

	@Override
	public String getUsername() {
		System.out.print("Welcome to the chat application! Enter name to begin: ");
		name = sc.nextLine().trim();
		return name;
	}

	public void getInput() {
		System.out.println("Type 'menu' at any time for more options.");
		while (true) {
			message = sc.nextLine().trim();
			try {
				if (message.equalsIgnoreCase("Menu")) {
					displayMenu();
				}else
					helper.publishMessageToServer(name+ ": "+ message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	private void displayMenu() throws RemoteException {
		System.out.println("Options");
		System.out.println("==========");
		System.out.println("1. List registered users");
		System.out.println("2. Private message");
		System.out.println("3. Return to conversation");
		System.out.println("4. Exit conversation");
		int option = sc.nextInt();
		switch (option) {
			case 1:
				System.out.println("Registered Users:");
				System.out.println(helper.getRegisteredUsers().toString());
				System.out.println("\n");
				break;
			case 2:
				privateMessage();
				break;
			case 3:
				break;
			case 4:
				helper.removeRegisteredUser(name);
				helper.publishMessageToServer(name + " left the conversation.");
				System.exit(0);
		}
	}

	private void privateMessage() throws RemoteException {
		System.out.print("Private message user from the following list.");
		ArrayList<String> registeredUsers = helper.getRegisteredUsers();
		System.out.println(registeredUsers.toString());
		System.out.print("Select recipient: ");
		String receiver = sc.next();
		System.out.print("Type message: ");
		String message = sc.nextLine();

		helper.directMessage(receiver, "\nNew private message from " + name + ":" + message);

	}
}