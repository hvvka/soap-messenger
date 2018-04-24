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
    private Endpoint endpoint;

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
                int option = JOptionPane.showConfirmDialog(mainFrame, "Do you want to quit?");
                if (option == 0) {
                    stopServer();
                    mainFrame.dispose();
                }
            }
        };
    }

    private void stopServer() {
        if (server != null) {
            server.stopThread();
            server = null;
            endpoint.stop();
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
        mainFrame.setEnabledComponents(false);
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
                initializeConnection(port, name);
                initScheduler();
                setComponents(Color.GREEN, true);
            } else {
                setComponents(Color.RED, false);
            }
        });
    }

    private void setComponents(Color textBackgroundColor, boolean isEnabled) {
        userPortText.setBackground(textBackgroundColor);
        usernameText.setBackground(textBackgroundColor);
        mainFrame.setEnabledComponents(isEnabled);
    }

    private void initializeConnection(String port, String name) {
        portListClient = new PortListClient();
        usedPort = Integer.parseInt(port);
        server = new ServerImpl(Integer.parseInt(port), name, messageArea);
        endpoint = Endpoint.publish("http://localhost:" + port + "/ws/hello", server);
        portListClient.addUsedPort(usedPort);
    }

    private void addSendListener() {
        sendButton.addActionListener(e -> {
            String message = messageText.getText();
            String port = Objects.requireNonNull(receiverPortBox.getSelectedItem()).toString();
            server.sendMessage(message, Integer.parseInt(port));
            messageText.setText("");
        });
    }

    private void addPassListener() {
        passButton.addActionListener(e -> {
            List<Integer> portList = portListClient.getUsedPorts();
            String message = messageText.getText();
            Optional<Integer> portIndex = portList.stream()
                    .filter(p -> p == usedPort)
                    .map(portList::indexOf)
                    .findFirst();
            portIndex.ifPresent(index -> {
                int nextPort = index == portList.size() - 1 ? 0 : index + 1;
                if (portIndex.get() != nextPort)
                    server.sendMessage(message, portList.get(nextPort));
            });
            messageText.setText("");
        });
    }

    private void addBroadcastListener() {
        broadcastButton.addActionListener(e -> {
            String message = messageText.getText();
            for (Integer port : portListClient.getUsedPorts()) {
                if (port != usedPort)
                    server.sendMessage(message, port);
            }
            messageText.setText("");
        });
    }

    private void addDisconnectListener() {
        disconnectButton.addActionListener(e -> {
            stopServer();
            setComponents(Color.LIGHT_GRAY, false);
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
