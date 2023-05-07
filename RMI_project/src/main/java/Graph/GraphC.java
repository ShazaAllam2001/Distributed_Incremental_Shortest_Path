package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.server.*;

public class GraphC extends UnicastRemoteObject implements GraphI {

    public GraphC() throws Exception {
        super();
    }

    private HashMap<String, MyNode> nodes = new HashMap<String, MyNode>();

    public String buildTheGraph(ArrayList<String> lines) {
        for (String line : lines) {
            addPathBetween(line);
        }
        return "R";
    }

    public void addPathBetween(String line) {
        String[] nodeNames = line.split(" ");
        MyNode node = new MyNode();
        if (!nodes.containsKey(nodeNames[0])) {
            node.setNodeName(nodeNames[0]);
            nodes.put(nodeNames[0], node);
        }
        if (!nodeNames[0].equals(nodeNames[1])) {
            node = new MyNode();
            if (!nodes.containsKey(nodeNames[1])) {
                node.setNodeName(nodeNames[1]);
                nodes.put(nodeNames[1], node);
            }
            node = new MyNode();
            node = nodes.get(nodeNames[0]);
            node.addNodeToCanGoTo(nodeNames[1]);
            nodes.replace(nodeNames[0], node);
            node = new MyNode();
            node = nodes.get(nodeNames[1]);
            node.addNodeToCanGetReachToFrom(nodeNames[0]);
            nodes.replace(nodeNames[1], node);
        }
    }

    public void deletePathBetween(String line) {
        String[] nodeNames = line.split(" ");
        MyNode node = new MyNode();
        if (nodes.containsKey(nodeNames[0])) {
            node = nodes.get(nodeNames[0]);
            node.deleteNodeFromCanGoTo(nodeNames[1]);
            nodes.replace(nodeNames[0], node);
        }
        if (nodes.containsKey(nodeNames[1])) {
            node = new MyNode();
            node = nodes.get(nodeNames[1]);
            node.deleteNodeFromCanGetReachToFrom(nodeNames[0]);
            nodes.replace(nodeNames[1], node);
        }
    }
    public int numberOfTheShortestPathBetween(String line) {
        String[] nodeNames = line.split(" ");
        MyNode start = nodes.get(nodeNames[0]);
        MyNode goal = nodes.get(nodeNames[1]);
        int num = bidirectionalSearch(start, goal);
        //int num = BFS(start, goal);
        return num;
    }

    private int BFS(MyNode start, MyNode goal) {

        return -1;
    }
    private int bidirectionalSearch(MyNode start, MyNode goal) {

        return -1;
    }

    public HashMap<String, MyNode> getNodes() {
        return nodes;
    }
}
