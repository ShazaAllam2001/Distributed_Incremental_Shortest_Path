package RMI;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientRMI implements ClientI {
    private ServerI stub;
    private ClientI skeleton;
    private Registry registry;
    private final Lock lock;

    public ClientRMI() throws RemoteException, NotBoundException, AlreadyBoundException {
        this.skeleton = (ClientI) UnicastRemoteObject.exportObject(this, 0);
        this.registry = LocateRegistry.getRegistry("localhost", 1099);
        this.stub = (ServerI) registry.lookup("server");
        registry.bind("client", skeleton);
        this.lock = new ReentrantLock();
    }

    public ServerI getStub() {
        return stub;
    }

    public void returnShortestPath(ArrayList<Integer> values) throws RemoteException {
        for (int value : values) {
            System.out.println(value);
        }
    }
}
