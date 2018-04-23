package com.hania.controller;

import com.hania.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ClientThread implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ClientThread.class);

    private Server server;

    private Thread thread;

    public ClientThread(Server server) {
        this.server = server;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        LOG.info("Listening thread has started");
        while (!Thread.currentThread().isInterrupted()) {
//            server.receiveMessage()
        }
    }

    synchronized void stopThread() {
        thread.interrupt();
    }
}
