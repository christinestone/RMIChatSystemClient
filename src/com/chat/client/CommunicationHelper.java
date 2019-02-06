package com.chat.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.chat.apis.ChatClient;
import com.chat.apis.ChatServer;
import com.chat.ui.UI;

public class CommunicationHelper {

	ChatServer server;

	public void publishMessageToServer(String message) throws RemoteException {
		server.broadcast(message);
	}

	public void directMessage(String recepientName, String message) throws RemoteException {
		server.privateMessage(recepientName, message);
	}

	public ArrayList<String> getRegisteredUsers() throws RemoteException {
		return server.listUsers();
	}
	
	public void removeRegisteredUser(String clientName) throws RemoteException {
		server.updateUsersList(clientName);
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
