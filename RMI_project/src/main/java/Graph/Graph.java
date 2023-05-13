package Graph;
import java.util.*;

public class Graph {
    private final HashMap<String, Node> nodes = new HashMap<>();

    public void addEdge(String source, String destination) {
        Node node = new Node();
        // put source node on hash map
        if(!nodes.containsKey(source)) {
            nodes.put(source, node);
        }
        if(!source.equals(destination)) {
            node = new Node();
            // put destination node on hash map
            if(!nodes.containsKey(destination)) {
                nodes.put(destination, node);
            }
            // add edges to each node
            node = nodes.get(source);
            node.addOutEdge(destination);
            nodes.replace(source, node);
            node = nodes.get(destination);
            node.addInEdge(source);
            nodes.replace(destination, node);
        }
    }

    public void deleteEdge(String source, String destination) {
        // delete Edge, then delete node if it became with no edges in or out
        if(nodes.containsKey(source) && nodes.containsKey(destination)) {
            Node node = nodes.get(source);
            node.deleteOutEdge(destination);
            nodes.replace(source, node);
            if((node.getInEdges().size()==0) && (node.getOutEdges().size()==0)) {
                nodes.remove(source);
            }
            node = nodes.get(destination);
            node.deleteInEdge(source);
            nodes.replace(destination, node);
            if((node.getInEdges().size()==0) && (node.getOutEdges().size()==0)) {
                nodes.remove(destination);
            }
        }
    }

    public int findShortestPath(String source, String destination) {
        return BDS(source, destination);
        //return BFS(source, destination
    }

    private int BFS(String source, String destination) {
        if(!nodes.containsKey(source) || !nodes.containsKey(destination)) {
            return -1;
        }
        if(source.equals(destination)) {
            return 0;
        }
        HashMap<String, String> myParent = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(source);
        Node node;
        while(!queue.isEmpty()) {
            String nodeName = queue.poll();
            if(visited.contains(nodeName)) {
                continue;
            }
            visited.add(nodeName);
            node = nodes.get(nodeName);
            if(nodeName.equals(destination)) {
                break;
            }
            for(String child : node.getOutEdges()) {
                if(!visited.contains(child) && !queue.contains(child)) {
                    queue.add(child);
                    myParent.put(child, nodeName);
                }
            }
        }
        int edgesCount = 0;
        // traverse down to get number of edges
        String child = destination;
        while (myParent.containsKey(child)) {
            child = myParent.get(child);
            edgesCount++;
        }
        if(edgesCount != 0) {
            return edgesCount;
        }
        return -1;
    }

    private int BDS(String source, String destination) {
        if(!nodes.containsKey(source) || !nodes.containsKey(destination)) {
            return -1;
        }
        if(source.equals(destination)) {
            return 0;
        }
        HashMap<String, String> myParent = new HashMap<>();
        HashMap<String, String> myChild = new HashMap<>();
        Queue<String> forwardQueue = new LinkedList<>();
        ArrayList<String> forwardVisited = new ArrayList<>();
        Queue<String> backwardQueue = new LinkedList<>();
        ArrayList<String> backwardVisited = new ArrayList<>();
        forwardQueue.add(source);
        backwardQueue.add(destination);
        String nodeName, middleNode = "";
        Node node;
        while(!forwardQueue.isEmpty() || !backwardQueue.isEmpty()) {
            if(!forwardQueue.isEmpty()) {
                nodeName = forwardQueue.remove();
                if(!forwardVisited.contains(nodeName)) {
                    forwardVisited.add(nodeName);
                    node = nodes.get(nodeName);
                    if(backwardVisited.contains(nodeName)) {
                        middleNode = nodeName;
                        break;
                    }
                    for(String child : node.getOutEdges()) {
                        if(!forwardVisited.contains(child) && !forwardQueue.contains(child)) {
                            forwardQueue.add(child);
                            myParent.put(child, nodeName);
                        }
                    }
                }
            }
            if(!backwardQueue.isEmpty()) {
                nodeName = backwardQueue.remove();
                if(!backwardVisited.contains(nodeName)) {
                    backwardVisited.add(nodeName);
                    node = nodes.get(nodeName);
                    if(forwardVisited.contains(nodeName)) {
                        middleNode = nodeName;
                        break;
                    }
                    for(String parent : node.getInEdges()) {
                        if(!backwardVisited.contains(parent) && !backwardQueue.contains(parent)) {
                            backwardQueue.add(parent);
                            myChild.put(parent, nodeName);
                        }
                    }
                }
            }
        }
        int edgesCount = 0;
        // traverse down to get number of edges
        String child = middleNode;
        while(myParent.containsKey(child)) {
            child = myParent.get(child);
            edgesCount++;
        }
        // traverse up to get number of edges
        String parent = middleNode;
        while(myChild.containsKey(parent)) {
            parent = myChild.get(parent);
            edgesCount++;
        }
        if(edgesCount != 0) {
            return edgesCount;
        }
        return -1;
    }
}