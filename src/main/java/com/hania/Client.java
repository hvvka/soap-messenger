package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Client implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private String name;

    private String host;

    private Socket socket;

    private Server server;

    private boolean running;

    public Client(String name) {
        this.name = name;
        Thread thread = new Thread(this);
        thread.start();
        running = true;
    }

    public static void main(String[] args) {
        new Client("Wrr");

//        try (Socket socket = new Socket()) {
//            URL url = new URL("http://localhost:9999/ws/hello?wsdl");
//            QName qname = new QName("http://hello.hania.com/", "ServerImplService");
//
//            Service service = Service.create(url, qname);
//            Server server = service.getPort(Server.class);
//
//            socket.connect(new InetSocketAddress(ServerImpl.SERVER_PORT));
//            server.sendMessage("");
//
//        } catch (IOException e) {
//            LOG.error("", e);
//        }
    }

    @Override
    public void run() {
        try {
            URL url = new URL("http://localhost:9999/ws/hello?wsdl");
            QName qname = new QName("http://hania.com/", "ServerImplService");
            Service service = Service.create(url, qname);
            server = service.getPort(Server.class);
            host = InetAddress.getLocalHost().getHostName();
            socket = new Socket(host, ServerImpl.SERVER_PORT);
        } catch (IOException e) {
            LOG.error("", e);
            return;
        }

        server.sendMessage("test");
        LOG.info(server.fetchMessages());

        while (running) {
//            server.fetchMessages();
//        }
//        while (server.fetchMessages().isEmpty()) {
//            try {
//                LOG.info("Waiting...");
//                socket.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
