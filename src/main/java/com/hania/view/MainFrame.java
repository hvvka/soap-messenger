package com.hania.view;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 350;

    private JPanel mainPanel;
    private JTextField usernameText;
    private JTextField userPortText;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextArea messageArea;
    private JTextField messageText;
    private JButton sendButton;
    private JComboBox receiverPortBox;

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

    public JButton getDisconnectButton() {
        return disconnectButton;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public JTextField getMessageText() {
        return messageText;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JComboBox getReceiverPortBox() {
        return receiverPortBox;
    }
}
