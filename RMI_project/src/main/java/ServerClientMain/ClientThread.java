package ServerClientMain;

import RMI.ClientRMI;

public class ClientThread implements Runnable {
    private int threadID;

    public ClientThread(int threadID) {
        this.threadID = threadID;
    }

    @Override
    public void run() {
        try {
            ClientRMI clientRMI = new ClientRMI(threadID);
            clientRMI.runFile("input.txt");
            //clientRMI.runStdInput();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
