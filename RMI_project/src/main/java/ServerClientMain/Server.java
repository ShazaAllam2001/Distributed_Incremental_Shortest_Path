package ServerClientMain;

import RMI.ServerRMI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class Server {

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        ServerRMI serverRMI = new ServerRMI();

    }
}
