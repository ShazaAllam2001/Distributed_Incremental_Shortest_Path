package ServerClientMain;

import RMI.ClientRMI;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Client {
    public static final String path = "C:\\Users\\Blu-Ray\\Documents\\Distrubted_Incremental_Shortest_Path\\RMI_project\\src\\main\\java\\ServerClientMain\\";

    public static void main(String[] args) throws NotBoundException, RemoteException, FileNotFoundException {
        ClientRMI clientRMI = new ClientRMI();
        File input = new File(path + "input.txt");
        Scanner scanner = new Scanner(input);
        String line;
        Queue<String> lines = new LinkedList<>();
        boolean initialized = false;
        while(true) {
            line = scanner.nextLine();
            if(line.equals("exit")) {
                break;
            }
            if(!initialized) {
                if(line.equals("S")) {
                    System.out.println(clientRMI.getStub().buildGraph(lines));
                    lines = new LinkedList<>();
                    initialized = true;
                }
            }
            else {
                if(line.equals("F")) {
                    System.out.println(clientRMI.getStub().processBatch(lines));
                    lines = new LinkedList<>();
                }
            }
            lines.add(line);
        }
        scanner.close();
    }
}
