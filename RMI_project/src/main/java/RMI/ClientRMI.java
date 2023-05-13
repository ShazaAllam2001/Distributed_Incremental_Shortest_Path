package RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
    private Registry registry;
    private ServerI stub;

    public ClientRMI() throws RemoteException, NotBoundException {
        this.registry = LocateRegistry.getRegistry("localhost", 1099);
        this.stub = (ServerI) registry.lookup("server");
    }

    public ServerI getStub() {
        return stub;
    }

}
