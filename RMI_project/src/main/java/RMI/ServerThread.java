package RMI;

public class ServerThread implements Runnable {
    private String line;
    private int clientID;

    public ServerThread(String line, int clientID) {
        this.line = line;
        this.clientID = clientID;
    }

    @Override
    public void run() {
        char operation = line.charAt(0);
        String[] nodeNames = line.split(" ");
        if(operation == 'A') {
            ServerRMI.lockRW.writeLock().lock();
            try {
                ServerRMI.graphObj.addEdge(nodeNames[1],nodeNames[2]);
            } catch(Exception e) {
                e.printStackTrace();
            }
            ServerRMI.lockRW.writeLock().unlock();
            ServerRMI.logger.info("Client " + clientID + " : Add Edge " + nodeNames[1] + " -> " + nodeNames[2] + " request completed successfully");
        }
        else if(operation == 'D') {
            ServerRMI.lockRW.writeLock().lock();
            try {
                ServerRMI.graphObj.deleteEdge(nodeNames[1],nodeNames[2]);
            } catch(Exception e) {
                e.printStackTrace();
            }
            ServerRMI.lockRW.writeLock().unlock();
            ServerRMI.logger.info("Client " + clientID + " : Delete Edge " + nodeNames[1] + " -> " + nodeNames[2] + " request completed successfully");
        }
        else if(operation == 'Q') {
            int shortestPath = -1;
            ServerRMI.lockRW.readLock().lock();
            try {
                shortestPath = ServerRMI.graphObj.findShortestPath(nodeNames[1], nodeNames[2]);
            } catch(Exception e) {
                e.printStackTrace();
            }
            ServerRMI.lockRW.readLock().unlock();
            ServerRMI.logger.info("Shortest Path request completed successfully, returning response: " + shortestPath);
            ServerRMI.results.add(String.valueOf(shortestPath));
        }
    }
}
