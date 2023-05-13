package RMI;
import Graph.Graph;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class ServerRMI implements ServerI {
    private final Graph graphObj = new Graph();
    private final ServerI stub;
    private final ClientI skeleton;
    private final Registry registry;
    private final Lock lock;
    private final Queue<ArrayList<String>> batches = new LinkedList<>();

    public ServerRMI() throws RemoteException, AlreadyBoundException, NotBoundException {
        this.stub = (ServerI) UnicastRemoteObject.exportObject(this, 0);  // change port to configured one
        this.registry = LocateRegistry.createRegistry(1099); // change port to configured one
        this.skeleton = (ClientI) registry.lookup("client");
        registry.bind("server", stub);
        this.lock = new ReentrantLock();
    }

    public ClientI getSkeleton() {
        return skeleton;
    }

    public String buildGraph(ArrayList<String> lines) throws RemoteException {
        for (String line : lines) {
            String[] nodeNames = line.split(" ");
            graphObj.addEdge(nodeNames[0], nodeNames[1]);
        }
        serverThread.start();
        return "R";
    }

    public void addBatch(ArrayList<String> batch) throws RemoteException {
        batches.add(batch);
    }

    private final Thread serverThread = new Thread(() -> {
        ArrayList<String> batch;
        while (true) {
            if (!batches.isEmpty()) {
                ArrayList<Integer> shortestPath = new ArrayList<>();
                batch = batches.remove();
                for (String line : batch) {
                    char operation = line.charAt(0);
                    String[] nodeNames = line.split(" ");
                    if (operation == 'A') {
                        graphObj.addEdge(nodeNames[1], nodeNames[2]);
                    } else if (operation == 'D') {
                        graphObj.deleteEdge(nodeNames[1], nodeNames[2]);
                    } else if (operation == 'Q') {
                        shortestPath.add(graphObj.findShortestPath(nodeNames[1], nodeNames[2]));
                    }
                }
                try {
                    getSkeleton().returnShortestPath(shortestPath);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
}