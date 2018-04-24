package com.hania.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class PortListClient {

    private static final Logger LOG = LoggerFactory.getLogger(PortListClient.class);

    private static final String SERVER_NAME = "PortList";

    List<Integer> getUsedPorts() {
        try {
            PortListRemote remoteServer = getServer();
            if (remoteServer != null) {
                LOG.info("Port list: {}", remoteServer.getUsedPorts());
                return remoteServer.getUsedPorts();
            }
        } catch (RemoteException e) {
            LOG.error("", e);
        }
        return Collections.emptyList();
    }

    void addUsedPort(Integer usedPort) {
        try {
            PortListRemote remoteServer = getServer();
            if (remoteServer != null) {
                remoteServer.addUsedPort(usedPort);
            }
        } catch (RemoteException e) {
            LOG.error("", e);
        }
    }

    void removeUsedPort(Integer usedPort) {
        try {
            PortListRemote remoteServer = getServer();
            if (remoteServer != null) {
                remoteServer.removeUsedPort(usedPort);
            }
        } catch (RemoteException e) {
            LOG.error("", e);
        }
    }

    private PortListRemote getServer() throws RemoteException {
        PortListRemote remoteServer = null;
        Registry registry = getRegistry();
        try {
            remoteServer = (PortListRemote) registry.lookup(SERVER_NAME);
            LOG.info("PortListRemote {} lookup succeed.", SERVER_NAME);
        } catch (NotBoundException e) {
            LOG.error("", e);
        }
        return remoteServer;
    }

    private Registry getRegistry() throws RemoteException {
        return LocateRegistry.getRegistry(2099);
    }
}
