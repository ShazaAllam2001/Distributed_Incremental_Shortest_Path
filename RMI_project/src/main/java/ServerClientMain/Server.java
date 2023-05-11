package ServerClientMain;

import Graph.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class Server {
    private Queue<String> batches = new LinkedList<>();
    private Graph graph = new Graph();

    public String buildGraph() {
        while(!batches.isEmpty()) {
            String line = batches.remove();
            if(line.equals("S")) {
                break;
            }
            String[] nodeNames = line.split(" ");
            graph.addEdge(nodeNames[0], nodeNames[1]);
        }
        return "R";
    }

    private void chooseOperation() {
        String line;
        while(true) {
            if(!batches.isEmpty()) {
                line = batches.remove();
                char operation = line.charAt(0);
                if(operation == 'F') {
                    break;
                }
                String[] nodeNames = line.split(" ");
                if(operation == 'A') {
                    graph.addEdge(nodeNames[1],nodeNames[2]);
                }
                else if(operation == 'D') {
                    graph.deleteEdge(nodeNames[1],nodeNames[2]);
                }
                else if(operation == 'Q') {
                    System.out.println(graph.getShortestPath1(nodeNames[1],nodeNames[2]));
                    //System.out.println(graph.getShortestPath2(nodeNames[1],nodeNames[2]));
                }
            }
        }
    }
}
