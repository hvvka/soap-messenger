package com.hania.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public interface PortListRemote extends Remote {

    List<Integer> getUsedPorts() throws RemoteException;

    void addUsedPort(Integer usedPort) throws RemoteException;

    void removeUsedPort(Integer usedPort) throws RemoteException;
}
