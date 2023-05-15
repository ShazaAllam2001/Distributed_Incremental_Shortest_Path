package Util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class RequestsGenerator {
    private double pRead, pWrite;
    private int maxNodeID;

    public RequestsGenerator(double pWrite, int maxNodeID) {
        this.pWrite = pWrite;
        this.pRead = 1 - pWrite;
        this.maxNodeID = maxNodeID;
    }

    public Queue<String> getNRequests(int n) {
        Queue<String> requests = new LinkedList<>();
        for(int i=0; i<n; i++) {
            requests.add(getRequest());
        }
        return requests;
    }

    public String getRequest() {
        String type = randomType();
        int node1 = randomOperand();
        int node2 = randomOperandNotEqual(node1);
        return type + " " + node1 + " " + node2;
    }

    private String randomType() {
        double r1 = Math.random();
        if(r1 < pWrite) {
            double r2 = Math.random();
            if(r2 < 0.5)  return "A";
            else  return "D";
        }
        else {
            return "Q";
        }
    }

    private int randomOperand() {
        return ThreadLocalRandom.current().nextInt(1, maxNodeID + 1);
    }

    private int randomOperandNotEqual(int node1) {
        int node2 = ThreadLocalRandom.current().nextInt(1, maxNodeID + 1);
        while(node2 == node1) {
            node2 = ThreadLocalRandom.current().nextInt(1, maxNodeID + 1);
        }
        return node2;
    }
}
