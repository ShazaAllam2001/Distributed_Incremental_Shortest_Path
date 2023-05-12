package RMI;

import Graph.GraphI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
    private Registry registry;
    private GraphI stub;

    public ClientRMI() throws RemoteException, NotBoundException {
        this.registry = LocateRegistry.getRegistry("localhost", 1099);
        this.stub = (GraphI) registry.lookup("graph");
    }

    public void addEdge(String source, String destination) throws RemoteException {
        stub.addEdge(source, destination);
    }

    public void deleteEdge(String source, String destination) throws RemoteException {
        stub.deleteEdge(source, destination);
    }

    public int BFS(String source, String destination) throws RemoteException {
        return stub.BFS(source, destination);
    }

    public int BDS(String source, String destination) throws RemoteException {
        return stub.BDS(source, destination);
    }

}
