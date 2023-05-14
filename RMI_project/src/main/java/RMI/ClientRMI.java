package RMI;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientRMI {
    //public static final String path = "C:\\Users\\Blu-Ray\\Documents\\Distrubted_Incremental_Shortest_Path\\RMI_project\\src\\main\\java\\ServerClientMain\\";
    private Registry registry;
    private ServerI stub;
    private Logger logger;

    public ClientRMI() throws RemoteException, NotBoundException {
        System.out.println("Initializing client");
        this.registry = LocateRegistry.getRegistry("localhost", 1099);
        this.stub = (ServerI) registry.lookup("server");
        initLogger();
    }

    private void initLogger() {
        System.setProperty("name", "client");
        String dir = "clientLogs";
        System.setProperty("log.directory", dir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));

        File directory = new File(dir);
        if(!directory.exists()){
            directory.mkdir();
        }

        logger = LogManager.getLogger(ClientRMI.class);
        PropertyConfigurator.configure(ServerI.path + "Configs/logs-log4j.properties");
    }

    public void run() throws FileNotFoundException, RemoteException, InterruptedException {
        File input = new File(ServerI.path + "ServerClientMain\\input.txt");
        Scanner scanner = new Scanner(input);
        String line;
        Queue<String> lines = new LinkedList<>();
        while(true) {
            line = scanner.nextLine();
            if(line.equals("exit")) {
                break;
            }
            if(!stub.getInitialized() && line.equals("S")) {
                System.out.print(stub.buildGraph(lines));
                Thread.sleep(5000); // sleep for 5 sec
                lines = new LinkedList<>();
                stub.setInitialized(true);
            }
            if(stub.getInitialized() && line.equals("F")) {
                logger.info("requests sent: " + lines);
                long startTime = System.currentTimeMillis();
                List<String> results = stub.processBatch(lines);
                long responseTime = System.currentTimeMillis() - startTime;
                for(String result : results) {
                    System.out.println(result);
                }
                logger.info("response: " + results);
                logger.info("response time: " + responseTime + " ms");
                Thread.sleep(5000); // sleep for 5 sec
                lines = new LinkedList<>();
            }
            lines.add(line);
        }
        scanner.close();
    }

}
