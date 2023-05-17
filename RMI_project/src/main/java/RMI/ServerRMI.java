package RMI;

import Graph.Graph;
import Graph.GraphI;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerRMI implements ServerI {
    private GraphI graphObj;
    private ServerI skeleton;
    private Registry registry;
    private boolean initialized;
    private ReadWriteLock lockRW;
    private Lock lock;
    private Logger logger;

    public ServerRMI() throws RemoteException, AlreadyBoundException {
        System.out.println("Initializing Server");
        this.graphObj = new Graph();
        this.initialized = false;
        this.skeleton = (ServerI) UnicastRemoteObject.exportObject(this, 0);  // change port to configured one
        this.registry = LocateRegistry.createRegistry(1099); // change port to configured one
        registry.bind("server", skeleton);
        this.lock = new ReentrantLock();
        this.lockRW = new ReentrantReadWriteLock();
        initLogger();
    }

    private void initLogger() {
        System.setProperty("name", "server");
        String dir = "serverLogs";
        System.setProperty("log.directory", dir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));
        System.setProperty("client.id", "");

        File directory = new File(dir);
        if(!directory.exists()){
            directory.mkdir();
        }

        logger = LogManager.getLogger(ServerRMI.class);
        PropertyConfigurator.configure(ServerI.path + "Configs/logs-log4j.properties");
    }

    @Override
    public boolean getInitialized() {
        return initialized;
    }

    @Override
    public String buildGraph(Queue<String> lines, int clientID) throws RemoteException {
        String line;
        if(!this.initialized) {
            this.initialized = true;
            System.out.println("Initializing Graph");
            logger.info("Initializing Graph");
            while(!lines.isEmpty()) {
                line = lines.poll();
                System.out.println(line);
                String[] nodeNames = line.split(" ");
                graphObj.addEdge(nodeNames[0], nodeNames[1]);
                logger.info("Client " + clientID + " : Add Edge " + nodeNames[0] + " -> " + nodeNames[1] + " request completed successfully");
            }
            logger.info("Graph completed initializing successfully by client " + clientID);
            return "R\n";
        }
        return "Graph is already built\n";
    }

    @Override
    public List<String> processBatch(Queue<String> lines, int clientID) throws RemoteException {
        List<String> results = new ArrayList<>();
        String line;
        lock.lock();
        System.out.println("Starting new Batch");
        while(!lines.isEmpty()) {
            line = lines.poll();
            System.out.println(line);
            logger.info("received request: " + line);
            char operation = line.charAt(0);
            String[] nodeNames = line.split(" ");
            if(operation == 'A') {
                //lock.writeLock().lock();
                graphObj.addEdge(nodeNames[1],nodeNames[2]);
                //lock.writeLock().unlock();
                logger.info("Client " + clientID + " : Add Edge " + nodeNames[1] + " -> " + nodeNames[2] + " request completed successfully");
            }
            else if(operation == 'D') {
                //lock.writeLock().lock();
                graphObj.deleteEdge(nodeNames[1],nodeNames[2]);
                //lock.writeLock().unlock();
                logger.info("Client " + clientID + " : Delete Edge " + nodeNames[1] + " -> " + nodeNames[2] + " request completed successfully");
            }
            else if(operation == 'Q') {
                //lock.readLock().lock();
                int shortestPath = graphObj.findShortestPath(nodeNames[1], nodeNames[2]);
                //lock.readLock().unlock();
                logger.info("Shortest Path request completed successfully, returning response: " + shortestPath);
                results.add(String.valueOf(shortestPath));
            }
        }
        lock.unlock();
        return results;
    }

}