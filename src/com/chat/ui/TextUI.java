package com.chat.ui;

import java.rmi.RemoteException;
import java.util.Scanner;

import com.chat.apis.ChatClient;
import com.chat.client.ChatClientImpl;
import com.chat.client.CommunicationHelper;

public class TextUI implements UI {
    Scanner sc = new Scanner(System.in);
    CommunicationHelper helper;
    ChatClient client = new ChatClientImpl(this);
    String message;

    public TextUI() {
    	
    }
    
    public TextUI(CommunicationHelper helper) {
        this.helper = helper;
        helper.rmiSetup(this);
    }

    @Override
    public void publishMessageToUI(String message) {
        System.out.println(message);
    }

    @Override
    public void getInputFromUI(String name) {
        System.out.println("Type 'menu' for more options. \nEnter message: ");
        while (true) {
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
    public UI getUIType() {
        return new TextUI();
    }

    @Override
    public String getUsername() {
        System.out.print("Enter name: ");
        return sc.nextLine();
    }

    public void displayMenu() throws RemoteException {
        System.out.println("Options");
        System.out.println("==========");
        System.out.println("1. List Registered Users");
        System.out.println("2. Private Message");
        System.out.println("3. Exit");
        int option = sc.nextInt();
        switch (option) {
        case 1:
            helper.getListOfRegisteredUsers();
            break;
        case 2:
        	/*
        	 * TO DO : complete privateMessage() method
        	 */
            break;
        case 3:
        	System.exit(0);

        }
    }

    public void privateMessage() throws RemoteException {
        System.out.print("Select user to private message: ");
        String user = sc.next();
        System.out.print("Type message: ");
        String message = sc.nextLine();

        helper.directMessage(user, message);
    }
}
