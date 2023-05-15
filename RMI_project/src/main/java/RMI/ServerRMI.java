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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerRMI implements ServerI {
    private GraphI graphObj;
    private ServerI skeleton;
    private Registry registry;
    private boolean initialized;
    private ReadWriteLock lock;
    private Logger logger;

    public ServerRMI() throws RemoteException, AlreadyBoundException {
        System.out.println("Initializing server");
        this.graphObj = new Graph();
        this.initialized = false;
        this.skeleton = (ServerI) UnicastRemoteObject.exportObject(this, 0);  // change port to configured one
        this.registry = LocateRegistry.createRegistry(1099); // change port to configured one
        registry.bind("server", skeleton);
        this.lock = new ReentrantReadWriteLock();
        initLogger();
    }

    private void initLogger() {
        System.setProperty("name", "server");
        String dir = "serverLogs";
        System.setProperty("log.directory", dir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));

        File directory = new File(dir);
        if(!directory.exists()){
            directory.mkdir();
        }

        logger = LogManager.getLogger(ServerRMI.class);
        PropertyConfigurator.configure(ServerI.path + "Configs/logs-log4j.properties");
    }

    @Override
    public void setInitialized(boolean initialized) {
        if(!this.initialized) {
            this.initialized = initialized;
        }
    }

    @Override
    public boolean getInitialized() {
        return initialized;
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
        return "R\n";
    }

    @Override
    public List<String> processBatch(Queue<String> lines) throws RemoteException {
        List<String> results = new ArrayList<>();
        String line;
        while(!lines.isEmpty()) {
            line = lines.remove();
            System.out.println(line);
            logger.info("received request: " + line);

            char operation = line.charAt(0);
            String[] nodeNames = line.split(" ");
            if(operation == 'A') {
                lock.writeLock().lock();
                graphObj.addEdge(nodeNames[1],nodeNames[2]);
                lock.writeLock().unlock();
                logger.info("Add Edge request completed successfully");
                logger.info("Number of nodes: " + graphObj.getNumberOfNodes());
            }
            else if(operation == 'D') {
                lock.writeLock().lock();
                graphObj.deleteEdge(nodeNames[1],nodeNames[2]);
                lock.writeLock().unlock();
                logger.info("Delete Edge request completed successfully");
            }
            else if(operation == 'Q') {
                lock.readLock().lock();
                int shortestPath = graphObj.findShortestPath(nodeNames[1], nodeNames[2]);
                lock.readLock().unlock();
                logger.info("Shortest Path request completed successfully, returning response: " + shortestPath);
                results.add(String.valueOf(shortestPath));
            }
        }
        return results;
    }

}