package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Queue;

public interface ServerI extends Remote {
    String path = "C:\\Users\\Blu-Ray\\Documents\\Distrubted_Incremental_Shortest_Path\\RMI_project\\src\\main\\java\\";

    boolean getInitialized() throws RemoteException;

    String buildGraph(Queue<String> lines, int clientID) throws RemoteException;

    List<String> processBatchUnparalleled(Queue<String> lines, int clientID) throws RemoteException;

    List<String> processBatch(Queue<String> lines, int clientID) throws RemoteException, InterruptedException;
}