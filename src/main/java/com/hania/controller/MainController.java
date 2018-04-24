package com.hania.controller;

import com.hania.Server;
import com.hania.ServerImpl;
import com.hania.view.MainFrame;

import javax.swing.*;
import javax.xml.ws.Endpoint;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainController {

    private MainFrame mainFrame;
    private JTextField usernameText;
    private JTextField userPortText;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextArea messageArea;
    private JTextField messageText;
    private JButton sendButton;
    private JComboBox receiverPortBox;
    private JButton passButton;
    private JButton broadcastButton;

    private Server server;
    private int usedPort;
    private PortListClient portListClient;

    public MainController() {
        server = null;
        portListClient = new PortListClient();
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
                    stopServer();
                    System.exit(0);
                }
            }
        };
    }

    private void stopServer() {
        if (server != null) {
            server.stopThread();
            portListClient.removeUsedPort(usedPort);
        }
    }

    private void initComponents() {
        usernameText = mainFrame.getUsernameText();
        userPortText = mainFrame.getUserPortText();
        connectButton = mainFrame.getConnectButton();
        disconnectButton = mainFrame.getDisconnectButton();
        messageArea = mainFrame.getMessageArea();
        messageText = mainFrame.getMessageText();
        receiverPortBox = mainFrame.getReceiverPortBox();
        sendButton = mainFrame.getSendButton();
        passButton = mainFrame.getPassButton();
        broadcastButton = mainFrame.getBroadcastButton();
        setEnabledComponents(false);
    }

    private void setEnabledComponents(boolean isEnabled) {
        disconnectButton.setEnabled(isEnabled);
        messageArea.setEnabled(isEnabled);
        messageText.setEnabled(isEnabled);
        receiverPortBox.setEnabled(isEnabled);
        sendButton.setEnabled(isEnabled);
        passButton.setEnabled(isEnabled);
        broadcastButton.setEnabled(isEnabled);
    }

    private void initListeners() {
        addConnectListener();
        addSendListener();
        addDisconnectListener();
        addPassListener();
        addBroadcastListener();
    }

    private void addConnectListener() {
        connectButton.addActionListener(e -> {
            String port = userPortText.getText();
            String name = usernameText.getText();
            if ((!"".equals(name) || !"".equals(port)) && server == null) {
                usedPort = Integer.parseInt(port);
                server = new ServerImpl(Integer.parseInt(port), name, messageArea);
                Endpoint.publish("http://localhost:" + port + "/ws/hello", server);
                portListClient.addUsedPort(usedPort);
                initScheduler();
                userPortText.setBackground(Color.GREEN);
                usernameText.setBackground(Color.GREEN);
                setEnabledComponents(true);
            } else {
                userPortText.setBackground(Color.RED);
                usernameText.setBackground(Color.RED);
            }
        });
    }

    private void addSendListener() {
        sendButton.addActionListener(e -> {
            String message = messageText.getText();
            String port = Objects.requireNonNull(receiverPortBox.getSelectedItem()).toString();
            server.sendMessage(message, Integer.parseInt(port));
        });
    }

    private void addPassListener() {
        passButton.addActionListener(e -> {
            List<Integer> portList = portListClient.getUsedPorts();
            String message = messageText.getText();
            Optional<Integer> portIndex = portList.stream().map(p -> {
                if (p == usedPort) return portList.indexOf(p);
                else return 0;
            }).findFirst();
            portIndex.ifPresent(index -> {
                int nextPort = index == portList.size() - 1 ? 0 : index + 1;
                server.sendMessage(message, portList.get(nextPort));
            });
        });
    }

    private void addBroadcastListener() {
        broadcastButton.addActionListener(e -> {
            String message = messageText.getText();
            for (Integer port : portListClient.getUsedPorts()) {
                if (port != usedPort)
                    server.sendMessage(message, port);
            }
        });
    }

    private void addDisconnectListener() {
        disconnectButton.addActionListener(e -> {
            stopServer();
            setEnabledComponents(false);
        });
    }

    private void initScheduler() {
        Runnable getSelectedAnswer = this::refreshPortList;
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(getSelectedAnswer, 1, 10, TimeUnit.SECONDS);
    }

    private void refreshPortList() {
        receiverPortBox.removeAllItems();
        portListClient.getUsedPorts().forEach(p -> {
            if (p != usedPort) receiverPortBox.addItem(p);
        });
    }
}
