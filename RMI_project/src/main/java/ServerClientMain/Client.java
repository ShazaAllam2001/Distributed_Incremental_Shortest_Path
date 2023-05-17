package ServerClientMain;

import RMI.ServerI;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Properties;

public class Client {

    public static void main(String[] args) throws IOException, NotBoundException {
        Properties systemProps = new Properties();
        systemProps.load(new FileInputStream(ServerI.path + "Configs\\system.properties"));
        int numberOfClients = Integer.parseInt(systemProps.getProperty("GSP.numberOfNodes"));
        Thread[] threads = new Thread[numberOfClients];
        for(int i=0; i<numberOfClients; i++) {
            threads[i] = new Thread(new AutoClientThread(i+1));
            threads[i].start();
        }
    }
}

