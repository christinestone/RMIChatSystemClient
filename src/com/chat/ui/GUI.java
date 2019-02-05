package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.Border;

public class GUI extends JFrame implements ActionListener, UI {

	private static final Border CREATE_EMPTY_BORDER = BorderFactory.createEmptyBorder(10,10,10,10);
	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea;
	private JPanel userPanel, inputPanel, textPanel, buttonPanel;
	private JButton joinButton, sendButton, pmButton;
	private String username, message;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Create the frame
		frame = new JFrame("Chat Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

		JPanel outerPanel = new JPanel(new BorderLayout());
		outerPanel.add(getInputPanel(), BorderLayout.CENTER);
		outerPanel.add(getTextPanel(), BorderLayout.NORTH);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(outerPanel, BorderLayout.CENTER);
		contentPane.add(getUsersPanel(), BorderLayout.WEST);

		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocation(150, 150);
	}

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
	 * builds JPanel to display list of active users 
	 * @return clientPanel
	 */
	private void getClientPanel() {
		JPanel clientPanel = new JPanel(new BorderLayout());
		ListModel<String> listModel = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        clientPanel.add(listScrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);
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
			username = textField.getText();
			frame.setTitle(username + "'s console ");
			textField.setText("");
			textArea.append("username : " + username + " connecting to chat...\n");
		}
		if (e.getSource() == sendButton) {
			message = textField.getText();
			textField.setText("");
			textArea.append(username + ": " + message);
		}
		if (e.getSource() == pmButton) {

		}

	}

	@Override
	public void getInputFromUI(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UI getUIType() {
		return new GUI();
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void publishMessageToUI(String message) {
		// TODO Auto-generated method stub
		
	}

}
