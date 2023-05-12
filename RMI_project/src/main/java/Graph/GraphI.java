package Graph;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GraphI extends Remote {
    /**
     * add edge from source to destination
     * @param source starting node name
     * @param destination ending node name
     **/
    void addEdge(String source, String destination) throws RemoteException;

    /**
     * delete edge from source to destination
     * @param source starting node name
     * @param destination ending node name
     **/
    void deleteEdge(String source, String destination) throws RemoteException;

    /**
     * get the shortest path from source to destination using BFS
     * @param source starting node name
     * @param destination ending node name
     **/
    int BFS(String source, String destination) throws RemoteException;

    /**
     * get the shortest path from source to destination using Bidirectional Search
     * @param source starting node name
     * @param destination ending node name
     **/
    int BDS(String source, String destination) throws RemoteException;
}
