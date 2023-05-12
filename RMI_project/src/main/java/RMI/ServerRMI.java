package RMI;

import Graph.Graph;
import Graph.GraphI;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMI implements Remote {
    private GraphI graphObj;
    private GraphI stub;
    private Registry registry;

    public ServerRMI() throws RemoteException, AlreadyBoundException {
        this.graphObj = new Graph();
        this.stub = (GraphI) UnicastRemoteObject.exportObject(graphObj,0);  // change port to configured one
        this.registry = LocateRegistry.createRegistry(1099); // change port to configured one
        registry.bind("graph", stub);
    }

}
