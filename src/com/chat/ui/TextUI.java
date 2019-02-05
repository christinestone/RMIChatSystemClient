package com.chat.ui;

import java.rmi.RemoteException;
import java.util.Scanner;

import com.chat.apis.ChatClient;
import com.chat.client.ChatClientImpl;
import com.chat.client.CommunicationHelper;

public class TextUI implements UI {
    Scanner sc = new Scanner(System.in);
    CommunicationHelper helper = new CommunicationHelper();
    ChatClient client = new ChatClientImpl(this);
    String message;
    String name;

    public TextUI() {  	
 
    }
    
    public TextUI(CommunicationHelper helper) {
        this.helper = helper;
        helper.rmiSetup(this);
    }


    public void displayMessage(String input) {
        System.out.println(input);
    }


    public void startChat() {
        System.out.println("Type 'menu' for more options.");
        while (true) {
            System.out.print(name + ": ");
            message = sc.nextLine().trim();

            try {
                if (message.equalsIgnoreCase("Menu")) {
                    displayMenu();
                } else {
                    helper.sendMessageToServer(name, message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getUsername() {
        System.out.print("Welcome to the chat application! Enter name to begin: ");
        name = sc.nextLine().trim();
        startChat();
        return name;
    }

    private void displayMenu() throws RemoteException {
        System.out.println("Options");
        System.out.println("==========");
        System.out.println("1. List Registered Users");
        System.out.println("2. Private Message");
        System.out.println("3. Return to Chat");
        System.out.println("4. Exit");
        int option = sc.nextInt();
        switch (option) {
        case 1:
            helper.getListOfRegisteredUsers();
            break;
        case 2:
        	/*
        	 * TO DO : complete privateMessage() method
        	 */
        	privateMessage();
            break;
        case 3:
        	startChat();
        	break;
        case 4:
        	System.exit(0);
        }
    }

    public void privateMessage() throws RemoteException {
    	helper.getListOfRegisteredUsers();
        System.out.print("Select user to private message: ");
        String user = sc.next();
        System.out.print("Type message: ");
        String message = sc.nextLine();

        helper.directMessage(user, message);
    }
}