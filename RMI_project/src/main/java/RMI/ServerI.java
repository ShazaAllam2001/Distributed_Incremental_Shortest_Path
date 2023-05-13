package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;

public interface ServerI extends Remote {

    String buildGraph(Queue<String> lines) throws RemoteException;

    String processBatch(Queue<String> lines) throws RemoteException;
}
