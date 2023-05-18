package ServerClientMain;

import RMI.ClientRMI;

import java.io.IOException;
import java.rmi.NotBoundException;

public class Client2 {
    public static void main(String[] args) throws IOException, InterruptedException, NotBoundException {
       ClientRMI clientRMI = new ClientRMI(2);
       clientRMI.initializeGraph("input_graph.txt");
       clientRMI.runAutoGeneratedBatches(10);
    }
}
