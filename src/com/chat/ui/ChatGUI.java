package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatGUI implements UI {

    private JFrame frame;
    private JTextField usernameTextField;
    String name;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatGUI window = new ChatGUI();
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
    public ChatGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 461, 348);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel lblName = new JLabel("Name:");
        panel.add(lblName);

        usernameTextField = new JTextField();
        panel.add(usernameTextField);
        usernameTextField.setColumns(10);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                name = usernameTextField.getText();
            }
        });
        panel.add(btnSubmit);
    }

    @Override
    public void getInputFromUI(String name) {
        // TODO Auto-generated method stub

    }

    @Override
    public UI getUIType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public void publishMessageToUI(String message) {
        // TODO Auto-generated method stub

    }

}
