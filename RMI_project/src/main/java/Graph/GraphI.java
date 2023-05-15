package Graph;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GraphI extends Remote {
    /**
     * get the number of nodes in the graph
     **/
    int getNumberOfNodes();

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
     * get the shortest path length from source to destination
     * @param source starting node name
     * @param destination ending node name
     **/
    int findShortestPath(String source, String destination) throws RemoteException;

}
