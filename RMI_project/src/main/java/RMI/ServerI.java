package RMI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerI extends Remote {

    String buildGraph(ArrayList<String> lines) throws RemoteException;

    void addBatch(ArrayList<String> batch) throws RemoteException;
}