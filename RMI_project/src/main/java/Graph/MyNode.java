package Graph;

import java.util.ArrayList;

public class MyNode {
    private String name;
    private ArrayList<String> canGoTo = new ArrayList<>();
    private ArrayList<String> canGetReachToFrom = new ArrayList<>();

    public void setNodeName(String name) {
        this.name = name;
    }
    public String getNodeName() {
        return this.name;
    }
    public void addNodeToCanGoTo(String node) {
        if (!canGoTo.contains(node)) {
            canGoTo.add(node);
        }
    }
    public void deleteNodeFromCanGoTo(String node) {
        if (canGoTo.contains(node)) {
            canGoTo.remove(node);
        }
    }
    public ArrayList<String> getCanGoTo() {
        return this.canGoTo;
    }
    public void addNodeToCanGetReachToFrom(String node) {
        if (!canGetReachToFrom.contains(node)) {
            canGetReachToFrom.add(node);
        }
    }
    public void deleteNodeFromCanGetReachToFrom(String node) {
        if (canGetReachToFrom.contains(node)) {
            canGetReachToFrom.remove(node);
        }
    }
    public ArrayList<String> getCanGetReachToFrom() {
        return this.canGetReachToFrom;
    }
}

