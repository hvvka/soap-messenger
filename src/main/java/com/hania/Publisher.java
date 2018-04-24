package com.hania;

import com.hania.controller.MainController;
import com.hania.controller.PortListRemote;
import com.hania.controller.PortListRemoteImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Publisher {

    private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

    private static final int PARTICIPANT_NUMBER = 4;

    public static void main(String[] args) {
        new Thread(Publisher::startPortRegistry).start();
        for (int i = 0; i < PARTICIPANT_NUMBER; ++i) {
            Executors.newSingleThreadExecutor().execute(MainController::new);
        }
    }

    private static void startPortRegistry() {
        try {
            PortListRemote portListRemote = new PortListRemoteImpl();
            Registry registry = LocateRegistry.getRegistry(2099);
            registry.rebind("PortList", portListRemote);
            LOG.info("Port registry ready.");
        } catch (RemoteException e) {
            LOG.error("", e);
            exit(1);
        }
    }
}
