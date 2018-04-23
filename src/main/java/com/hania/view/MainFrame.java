package com.hania.view;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrame extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    private JPanel mainPanel;
    private JTextField usernameText;
    private JTextField userPortText;
    private JButton connectButton;
    private JButton disconnetButton;
    private JTextArea messageArea;
    private JTextField messageText;
    private JTextField receiverPortText;
    private JButton sendButton;

    public MainFrame() {
        super("SOAP messenger");
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JTextField getUsernameText() {
        return usernameText;
    }

    public JTextField getUserPortText() {
        return userPortText;
    }

    public JButton getConnectButton() {
        return connectButton;
    }

    public JButton getDisconnetButton() {
        return disconnetButton;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public JTextField getMessageText() {
        return messageText;
    }

    public JTextField getReceiverPortText() {
        return receiverPortText;
    }

    public JButton getSendButton() {
        return sendButton;
    }
}
