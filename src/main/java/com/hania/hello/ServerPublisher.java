package com.hania.hello;

import javax.xml.ws.Endpoint;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ServerPublisher {

    public static void main(String[] args) {

        System.out.println("Beginning to publish Server now");
        Endpoint.publish("http://localhost:9999/ws/hello", new ServerImpl());
        System.out.println("Done publishing");
    }
}
