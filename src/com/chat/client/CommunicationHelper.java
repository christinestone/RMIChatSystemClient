package com.chat.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.chat.apis.ChatClient;
import com.chat.apis.ChatServer;
import com.chat.ui.UI;

public class CommunicationHelper {

	ChatServer server;

	public void sendMessageToServer(String clientName, String message) throws RemoteException {
		server.broadcast(clientName + ": " + message);
	}

	public void directMessage(String user, String message) throws RemoteException {
		server.privateMessage(user, message);
	}

	public void getListOfRegisteredUsers() throws RemoteException {
		System.out.println("Registered Users:");
		System.out.println(server.listUsers().toString());
		System.out.println("\n");
	}

	public void rmiSetup(UI uiType) {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry();
			server = (ChatServer) registry.lookup("chatServer");
			ChatClient client = new ChatClientImpl(uiType);
			ChatClient stub = (ChatClient) UnicastRemoteObject.exportObject(client, 0);

			String clientName = uiType.getUsername();

			server.register(stub, clientName);

		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
