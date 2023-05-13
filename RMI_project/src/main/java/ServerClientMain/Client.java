package ServerClientMain;
import RMI.ClientRMI;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static final String path = "C:\\Users\\Blu-Ray\\Documents\\Distrubted_Incremental_Shortest_Path\\RMI_project\\src\\main\\java\\ServerClientMain\\";

    public static void main(String[] args) throws NotBoundException, RemoteException, FileNotFoundException, AlreadyBoundException {
        ClientRMI clientRMI = new ClientRMI();
        File input = new File(path + "input.txt");
        Scanner scanner = new Scanner(input);
        String line;
        ArrayList<String> lines = new ArrayList<>();
        boolean initialized = false;
        while(true) {
            line = scanner.nextLine();
            if(line.equals("exit")) {
                break;
            }
            if(!initialized && line.equals("S")) {
                clientRMI.getStub().buildGraph(lines);
                lines = new ArrayList<>();
                initialized = true;
            }
            else if(initialized && line.equals("F")) {
                clientRMI.getStub().addBatch(lines);
                lines = new ArrayList<>();
            }
            lines.add(line);
        }
        scanner.close();
    }
}
