package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ClientThread implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ClientThread.class);

    private Server server;

    private Socket socket;

    private boolean running;

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        LOG.info("Client thread has started");
        while (running) {
//            LOG.info(server.fetchMessages());
//            try {
//                socket.wait();
//            } catch (InterruptedException e) {
//                LOG.error("", e);
//                Thread.currentThread().interrupt();
//            }
        }
    }
}
