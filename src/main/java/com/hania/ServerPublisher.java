package com.hania;

import com.hania.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ServerPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ServerPublisher.class);

    public static void main(String[] args) {

        new MainController();

//        String name = "";
//        int port = 9999;
//
//        LOG.info("Beginning to publish Server now");
//        Server server1 = new ServerImpl(9000, "Foo");
//        Server server2 = new ServerImpl(10000, "Bar");
//        Endpoint.publish("http://localhost:9000/ws/hello", server1);
//        Endpoint.publish("http://localhost:10000/ws/hello", server2);
//        server1.sendMessage("Hello", 10000);
//        LOG.info("Done publishing");
//        server1.stopThread();
//        server2.stopThread();
//
//        LOG.info("Beginning to publish Server now");
//        Server server3 = new ServerImpl(11000, "Baz");
//        Server server4 = new ServerImpl(12000, "Buzz");
//        Endpoint.publish("http://localhost:11000/ws/hello", server3);
//        Endpoint.publish("http://localhost:12000/ws/hello", server4);
//        server3.sendMessage("Hello", 12000);
//        LOG.info("Done publishing");
//        server3.stopThread();
//        server4.stopThread();
    }
}
