package ServerClientMain;

import RMI.ClientRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) throws NotBoundException, RemoteException {
        ClientRMI clientRMI = new ClientRMI();
        clientRMI.addEdge("0","1");
        clientRMI.addEdge("0","4");
        clientRMI.addEdge("0","2");
        clientRMI.addEdge("2","1");
        clientRMI.addEdge("1","3");
        clientRMI.addEdge("3","4");
        clientRMI.addEdge("4","2");
        clientRMI.addEdge("4","5");
        clientRMI.addEdge("4","6");
        clientRMI.addEdge("3","6");
        System.out.println(clientRMI.BDS("0","3"));

    }
}
