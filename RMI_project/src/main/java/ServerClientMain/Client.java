package ServerClientMain;

import RMI.ClientRMI;

public class Client {

    public static void main(String[] args) {
        try {
            ClientRMI clientRMI = new ClientRMI();
            clientRMI.run();
        }
        catch(Exception e) {
            System.out.println(e.getStackTrace().toString());
        }
    }
}

