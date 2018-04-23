package com.hania.controller;

import com.hania.Server;
import com.hania.ServerImpl;
import com.hania.view.MainFrame;

import javax.swing.*;
import javax.xml.ws.Endpoint;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainController {

    private MainFrame mainFrame;
    private JTextField usernameText;
    private JTextField userPortText;
    private JButton connectButton;
    private JButton disconnetButton;
    private JTextArea messageArea;
    private JTextField messageText;
    private JTextField receiverPortText;
    private JButton sendButton;

    private Server server;

    public MainController() {
        server = null;
        mainFrame = new MainFrame();
        mainFrame.addWindowListener(getWindowAdapter());
        initComponents();
        initListeners();
    }

    private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to quit?");
                if (option == 0) {
                    if (server != null) server.stopThread();
                    System.exit(0);
                }
            }
        };
    }

    private void initComponents() {
        usernameText = mainFrame.getUsernameText();
        userPortText = mainFrame.getUserPortText();
        connectButton = mainFrame.getConnectButton();
        disconnetButton = mainFrame.getDisconnetButton();
        messageArea = mainFrame.getMessageArea();
        messageText = mainFrame.getMessageText();
        receiverPortText = mainFrame.getReceiverPortText();
        sendButton = mainFrame.getSendButton();
        setEnabledComponents(false);
    }

    private void setEnabledComponents(boolean isEnabled) {
        disconnetButton.setEnabled(isEnabled);
        messageArea.setEnabled(isEnabled);
        messageText.setEnabled(isEnabled);
        receiverPortText.setEnabled(isEnabled);
        sendButton.setEnabled(isEnabled);
    }

    private void initListeners() {
        addConnectListener();
        sendButton.addActionListener(e -> {
            String message = messageText.getText();
            String port = receiverPortText.getText();
            server.sendMessage(message, Integer.parseInt(port));
        });
    }

    private void addConnectListener() {
        connectButton.addActionListener(e -> {
            String port = userPortText.getText();
            String name = usernameText.getText();
            if ((!"".equals(name) || !"".equals(port)) && server == null) {
                server = new ServerImpl(Integer.parseInt(port), name, messageArea);
                Endpoint.publish("http://localhost:" + port + "/ws/hello", server);
                userPortText.setBackground(Color.GREEN);
                usernameText.setBackground(Color.GREEN);
                setEnabledComponents(true);
            } else {
                userPortText.setBackground(Color.RED);
                usernameText.setBackground(Color.RED);
            }
        });
    }
}
