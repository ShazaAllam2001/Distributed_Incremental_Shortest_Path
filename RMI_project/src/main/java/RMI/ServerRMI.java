package RMI;

import Graph.Graph;
import Graph.GraphI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerRMI implements ServerI {
    private GraphI graphObj;
    private ServerI stub;
    private Registry registry;
    private Lock lock;

    public ServerRMI() throws RemoteException, AlreadyBoundException {
        this.graphObj = new Graph();
        this.stub = (ServerI) UnicastRemoteObject.exportObject(this, 0);  // change port to configured one
        this.registry = LocateRegistry.createRegistry(1099); // change port to configured one
        registry.bind("server", stub);
        this.lock = new ReentrantLock();
    }

    @Override
    public String buildGraph(Queue<String> lines) throws RemoteException {
        String line;
        while(!lines.isEmpty()) {
            line = lines.poll();
            System.out.println(line);
            String[] nodeNames = line.split(" ");
            graphObj.addEdge(nodeNames[0], nodeNames[1]);
        }
        return "R";
    }

    @Override
    public String processBatch(Queue<String> lines) throws RemoteException {
        String results ="";
        String line;
        lock.lock();
        while(!lines.isEmpty()) {
            line = lines.remove();
            System.out.println(line);
            char operation = line.charAt(0);
            String[] nodeNames = line.split(" ");
            if(operation == 'A') {
                graphObj.addEdge(nodeNames[1],nodeNames[2]);
            }
            else if(operation == 'D') {
                graphObj.deleteEdge(nodeNames[1],nodeNames[2]);
            }
            else if(operation == 'Q') {
                results += graphObj.BFS(nodeNames[1],nodeNames[2]) + "\n";
                //results += graphObj.BDS(nodeNames[1],nodeNames[2]) + "\n";
            }
        }
        lock.unlock();
        return results;
    }

}