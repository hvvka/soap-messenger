package com.hania;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ServerPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(ServerPublisher.class);

    public static void main(String[] args) {

//        String name = args[0];
//        int port = Integer.parseInt(args[1]);
//        int layer = Integer.parseInt(args[2]);

        LOG.info("Beginning to publish Server now");
        Endpoint.publish("http://localhost:9999/ws/hello", new ServerImpl());
        LOG.info("Done publishing");
    }
}
