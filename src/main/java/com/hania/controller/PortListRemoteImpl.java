package com.hania.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PortListRemoteImpl extends UnicastRemoteObject implements PortListRemote {

    private List<Integer> usedPorts;

    public PortListRemoteImpl() throws RemoteException {
        super();
        usedPorts = new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PortListRemoteImpl that = (PortListRemoteImpl) o;

        return usedPorts != null ? usedPorts.equals(that.usedPorts) : that.usedPorts == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (usedPorts != null ? usedPorts.hashCode() : 0);
        return result;
    }
}
