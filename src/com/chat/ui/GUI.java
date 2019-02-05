package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.chat.client.CommunicationHelper;

public class GUI extends JFrame implements ActionListener, UI {
	private static final long serialVersionUID = 1L;
	private static final Border CREATE_EMPTY_BORDER = BorderFactory.createEmptyBorder(10,10,10,10);
	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea;
	private JPanel userPanel, inputPanel, textPanel, buttonPanel;
	private JButton joinButton, sendButton, pmButton;
	private String username, message;

	CommunicationHelper helper;
	Logger logger;

	/**
	 * Create the application.
	 */
	public GUI(CommunicationHelper helper) {
		initialize();
		frame.setVisible(true);
		this.helper = helper;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Chat Console");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

		JPanel outerPanel = new JPanel(new BorderLayout());
		outerPanel.add(getInputPanel(), BorderLayout.CENTER);
		outerPanel.add(getTextPanel(), BorderLayout.NORTH);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(outerPanel, BorderLayout.CENTER);
		contentPane.add(getUsersPanel(), BorderLayout.WEST);

		frame.add(contentPane);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocation(150, 150);
	}

	/**
	 * builds JPanel for list of active users 
	 * @return userPanel
	 */
	private JPanel getUsersPanel() {
		userPanel = new JPanel(new BorderLayout());

		JLabel usersLabel = new JLabel("Active Users", JLabel.CENTER);
		userPanel.add(usersLabel, BorderLayout.NORTH);
		userPanel.setBorder(CREATE_EMPTY_BORDER);
		userPanel.add(getButtonPanel(), BorderLayout.SOUTH);
		//getClientPanel();
		return userPanel;
	}

	/**
	 * builds JPanel to display chat text 
	 * @return textPanel
	 */
	private JPanel getTextPanel() {	
		textPanel = new JPanel();

		textArea = new JTextArea("Welcome! Enter name and click join to start.", 15, 35);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel.add(scrollPane);	

		return textPanel;
	}

	/**
	 * builds JPanel to take user input 
	 * @return inputPanel
	 */
	private JPanel getInputPanel() {
		inputPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		inputPanel.setBorder(CREATE_EMPTY_BORDER);
		textField = new JTextField();
		inputPanel.add(textField);
		return inputPanel;
	}

	/**
	 * builds JPanel to display buttons 
	 * @return buttonPanel
	 */
	private JPanel getButtonPanel() {
		buttonPanel = new JPanel(new GridLayout(4,1));
		buttonPanel.setBorder(CREATE_EMPTY_BORDER);

		joinButton = new JButton("Join");
		joinButton.addActionListener(this);
		buttonPanel.add(joinButton);

		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		sendButton.setEnabled(false);
		buttonPanel.add(sendButton);

		pmButton = new JButton("Private Message");
		pmButton.addActionListener(this);
		pmButton.setEnabled(false);	
		buttonPanel.add(pmButton);

		return buttonPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joinButton) {
			setUsername(textField.getText());
			textField.setText("");
			joinButton.setEnabled(false);
			sendButton.setEnabled(true);
			frame.setTitle(username + "'s Console ");
			textArea.append("\n" + username + " connecting to chat...\n");

			helper.rmiSetup(this);
		}
		if (e.getSource() == sendButton) {
			message = textField.getText();
			textField.setText("");
			try {
				helper.sendMessageToServer(username, message);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == pmButton) {
			message = textField.getText();
			textField.setText("");
			/*
			 * TO DO
			 */
		}
	}

	@Override
	public void displayMessage(String message) {
		textArea.append("\n" + message);
	}

	@Override
	public String getUsername() {
		if (username.length() == 0) {
			checkUsername();
		}
		return username;
	}

	private void checkUsername() {
		JOptionPane.showMessageDialog(frame, "Enter your name to Start");
		username = JOptionPane.showInputDialog("name");
	}

	private void setUsername(String username) {
		this.username = username;
	}
}