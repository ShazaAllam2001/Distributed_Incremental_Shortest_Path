package Graph;

import java.util.ArrayList;

public class Node {
    private String name;
    private int forwardDistance;
    private int backwardDistance;
    private ArrayList<String> outEdges = new ArrayList<>();
    private ArrayList<String> inEdges = new ArrayList<>();

    Node() {
        this.forwardDistance = 0;
        this.backwardDistance = 0;
    }

    public void setNodeName(String name) {
        this.name = name;
    }
    public String getNodeName() {
        return this.name;
    }

    public void setForwardDistance(int distance) {
        this.forwardDistance = distance;
    }
    public int getForwardDistance() {
        return this.forwardDistance;
    }

    public void setBackwardDistance(int distance) {
        this.backwardDistance = distance;
    }
    public int getBackwardDistance() {
        return this.backwardDistance;
    }

    public void addOutEdge(String node) {
        if(!outEdges.contains(node)) {
            outEdges.add(node);
        }
    }
    public void deleteOutEdge(String node) {
        outEdges.remove(node);
    }
    public ArrayList<String> getOutEdges() {
        return outEdges;
    }

    public void addInEdge(String node) {
        if(!inEdges.contains(node)) {
            inEdges.add(node);
        }
    }
    public void deleteInEdge(String node) {
        inEdges.remove(node);
    }
    public ArrayList<String> getInEdges() {
        return inEdges;
    }

}
