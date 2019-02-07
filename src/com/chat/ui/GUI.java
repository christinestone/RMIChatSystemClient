package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	private static JList<String> list;
	private DefaultListModel<String> listModel;

	static ArrayList<String> registeredUsers;

	CommunicationHelper helper;

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
		frame = new JFrame();
		frame.setSize(400, 400);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					helper.removeRegisteredUser(username);
					helper.publishMessageToServer(username + " left the conversation.");
					e.getWindow().dispose();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}     
            }
        });

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
	 * builds JPanel for users
	 * @return userPanel
	 */
	private JPanel getUsersPanel() {
		userPanel = new JPanel(new BorderLayout());

		JLabel usersLabel = new JLabel("Active Users", JLabel.CENTER);
		userPanel.add(usersLabel, BorderLayout.NORTH);
		userPanel.setBorder(CREATE_EMPTY_BORDER);
		userPanel.add(getButtonPanel(), BorderLayout.SOUTH);
		getClientPanel();
		return userPanel;
	}

	/**
	 * builds JPanel to display list of registered users 
	 * @return clientPanel
	 */
	private void getClientPanel() {
		JPanel clientPanel = new JPanel(new BorderLayout());
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JScrollPane listScrollPane = new JScrollPane(list);
		clientPanel.add(listScrollPane, BorderLayout.CENTER);
		userPanel.add(clientPanel, BorderLayout.CENTER);
	}

	/**
	 * builds JPanel to display conversation text 
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

		pmButton = new JButton("Private Message");
		pmButton.addActionListener(this);
		pmButton.setEnabled(false);	
		buttonPanel.add(pmButton);

		joinButton = new JButton("Join");
		joinButton.addActionListener(this);
		buttonPanel.add(joinButton);

		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		sendButton.setEnabled(false);
		buttonPanel.add(sendButton);

		return buttonPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (textField.getText().isEmpty() && joinButton.isEnabled()) {
			JOptionPane.showMessageDialog(frame, "Enter username to continue");
			return;
		}

		if (e.getSource() == joinButton) {
			setUsername(textField.getText());
			textField.setText("");
			joinButton.setEnabled(false);
			sendButton.setEnabled(true);
			frame.setTitle(username);
			textArea.append("\nConnecting to chat...\n");

			helper.rmiSetup(this);
			setActiveUsers();
		}

		refreshActiveUsers();
		message = username + ": " + textField.getText();

		if (e.getSource() == sendButton) {
			textField.setText("");
			try {
				helper.publishMessageToServer(message);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == pmButton) {	
			List<String> selectedRecipients = list.getSelectedValuesList();
			for (String receiver : selectedRecipients) {
				try {
					helper.directMessage(receiver, "\nNew private message from " + message);
					displayMessage(message);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			textField.setText("");
		}
	}

	/**
	 * listener method which invokes setActiveUsers upon update to textArea
	 */
	private void refreshActiveUsers() {
		DocumentListener dl = new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent event) {
				setActiveUsers();
			}

			@Override
			public void insertUpdate(DocumentEvent event) {
				setActiveUsers();
			}

			@Override
			public void removeUpdate(DocumentEvent event) {
				setActiveUsers();
			}
		};
		textArea.getDocument().addDocumentListener(dl);
	}

	/**
	 * method to update list of active users if list size changes
	 */
	private void setActiveUsers() {
		try {
			registeredUsers = helper.getRegisteredUsers();
			if (listModel.size() == registeredUsers.size()) {
				return;
			}
			else {	
				listModel.removeAllElements();
				for (String user: registeredUsers) {
					listModel.addElement(user);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (listModel.size() > 1) {
			pmButton.setEnabled(true);
		}else {
			pmButton.setEnabled(false);
		}
	}

	@Override
	public void displayMessage(String message) {
		textArea.append("\n" + message);
	}

	@Override
	public String getUsername() {
		if (username.length() == 0) {
			JOptionPane.showMessageDialog(frame, "Enter Name");
			username = JOptionPane.showInputDialog("name");
		}
		return username;
	}

	private void setUsername(String username) {
		this.username = username;
	}
}