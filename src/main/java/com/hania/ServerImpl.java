package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import javax.swing.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
@WebService(endpointInterface = "com.hania.Server")
public class ServerImpl implements Server, Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ServerImpl.class);

    private ServerSocket serverSocket;

    private int serverPort;

    private String host;

    private String name;

    private JTextArea messageArea;

    private Thread thread;

    public ServerImpl() {
    }

    public ServerImpl(int serverPort, String name, JTextArea messageArea) {
        this.name = name;
        this.serverPort = serverPort;
        this.messageArea = messageArea;
        waitForClients();
    }

    private void waitForClients() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public synchronized void sendMessage(String message, int port) {
        try {
            URL url = new URL("http://localhost:" + port + "/ws/hello?wsdl");
            QName qname = new QName("http://hania.com/", "ServerImplService");
            Service service = Service.create(url, qname);
            Server server = service.getPort(Server.class);
            host = InetAddress.getLocalHost().getHostName();
            server.receiveMessage(message, this.serverPort, this.name);
        } catch (IOException e) {
            LOG.error("", e);
        }
    }

    @Override
    public synchronized void receiveMessage(String message, int port, String name) {
        LOG.info(">>User {} received message from: {} on port {}", this.name, name, port);
        messageArea.append(name + "@" + port + ": " + message + "\n");
    }

    @Override
    public synchronized void stopThread() {
        try {
            serverSocket.close();
            thread.interrupt();
        } catch (IOException e) {
            LOG.error("", e);
        }
    }

    @Override
    public void run() {
        initializeNetworkConnections();
        while (!Thread.currentThread().isInterrupted()) {
            try (Socket socket = serverSocket.accept()) {
                LOG.info("Accepted client connection");
                Thread.sleep(5000);
            } catch (IOException e) {
                LOG.error("{} error: Cannot connect to client", this.name);
            } catch (InterruptedException e) {
                LOG.error("Thread was interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    private void initializeNetworkConnections() {
        try {
            host = InetAddress.getLocalHost().getHostName();
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            LOG.error("Server socket cannot be created", e);
            System.exit(0);
        }
        LOG.info("Server {} has started at host {}, on port {}", name, host, serverPort);
    }
}
