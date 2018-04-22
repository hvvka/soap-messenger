package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
@WebService(endpointInterface = "com.hania.Server")
public class ServerImpl implements Server, Runnable {

    static final int SERVER_PORT = 15000;
    private static final Logger LOG = LoggerFactory.getLogger(ServerImpl.class);
    private List<String> messages;

    private ServerSocket serverSocket;

    private String host;

    private boolean running;

    public ServerImpl() {
        messages = new ArrayList<>();
        running = true;
        waitForClients();
    }

    private void waitForClients() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public synchronized void sendMessage(String message) {
        messages.add(message);
    }

    @Override
    public synchronized String fetchMessages() {
        if (!messages.isEmpty()) return messages.get(messages.size() - 1);
        else return "";
    }

    @Override
    public synchronized void stopThread() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOG.error("", e);
        }
    }

    @Override
    public void run() {
        Socket socket;
        ClientThread clientThread;

        initializeNetworkConnections();

        while (running) {
            try {
                socket = serverSocket.accept();
                LOG.info("Accepted client connection");

                if (socket != null) {
                    clientThread = new ClientThread(this, socket);
                }
            } catch (IOException e) {
                LOG.error("SERVER ERROR: Cannot connect to client.");
            }
        }
    }

    private void initializeNetworkConnections() {
        try {
            host = InetAddress.getLocalHost().getHostName();
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            LOG.error("Server socket cannot be created", e);
            System.exit(0);
        }
        LOG.info("Server has started at host {}", host);
    }
}
