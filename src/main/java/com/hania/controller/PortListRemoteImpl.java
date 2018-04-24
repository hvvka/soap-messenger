package com.hania.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PortListRemoteImpl extends UnicastRemoteObject implements PortListRemote {

    private static final Logger LOG = LoggerFactory.getLogger(PortListRemoteImpl.class);

    private List<Integer> usedPorts;

    public PortListRemoteImpl() throws RemoteException {
        super();
        usedPorts = new ArrayList<>();
    }

    public static void main(String[] args) {
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

    @Override
    public List<Integer> getUsedPorts() {
        return usedPorts;
    }

    @Override
    public void addUsedPort(Integer usedPort) {
        usedPorts.add(usedPort);
    }

    @Override
    public void removeUsedPort(Integer usedPort) {
        usedPorts.remove(usedPort);
    }
}
