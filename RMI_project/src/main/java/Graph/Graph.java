package Graph;

import java.util.*;

public class Graph implements GraphI {
    private HashMap<String, Node> nodes = new HashMap<>();

    @Override
    public void addEdge(String source, String destination) {
        Node node = new Node();
        // put source node on hash map
        if(!nodes.containsKey(source)) {
            node.setNodeName(source);
            nodes.put(source, node);
        }
        if(!source.equals(destination)) {
            node = new Node();
            // put destination node on hash map
            if(!nodes.containsKey(destination)) {
                node.setNodeName(destination);
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

    @Override
    public void deleteEdge(String source, String destination) {
        Node node;
        if(nodes.containsKey(source)) {
            node = nodes.get(source);
            node.deleteOutEdge(destination);
            nodes.replace(source, node);
        }
        if(nodes.containsKey(destination)) {
            node = nodes.get(destination);
            node.deleteInEdge(source);
            nodes.replace(destination, node);
        }
    }

    private void resetDistances() {
        for(Node node: nodes.values()){
            node.setForwardDistance(0);
            node.setBackwardDistance(0);
        }
    }

    @Override
    public int getShortestPath1(String source, String destination) {
        if(!nodes.containsKey(source) || !nodes.containsKey(destination)) {
            return -1;
        }
        if(source.equals(destination)) {
            return 0;
        }


        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(source);

        Node node = null;
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
                    nodes.get(child).setForwardDistance(node.getForwardDistance()+1);
                }
            }
        }

        if(node != null) {
            int shortestPathLength = node.getForwardDistance();
            resetDistances();
            if(shortestPathLength != 0) {
                return shortestPathLength;
            }
        }
        return -1;
    }

    @Override
    public int getShortestPath2(String source, String destination) {
        if(!nodes.containsKey(source) || !nodes.containsKey(destination)) {
            return -1;
        }
        if(source.equals(destination)) {
            return 0;
        }

        Queue<String> forwardQueue = new LinkedList<>();
        ArrayList<String> forwardVisited = new ArrayList<>();
        Queue<String> backwardQueue = new LinkedList<>();
        ArrayList<String> backwardVisited = new ArrayList<>();
        forwardQueue.add(source);
        backwardQueue.add(destination);

        String nodeName;
        Node node = null;
        while(!forwardQueue.isEmpty() || !backwardQueue.isEmpty()) {
            if(!forwardQueue.isEmpty()) {
                nodeName = forwardQueue.remove();
                if(!forwardVisited.contains(nodeName)) {
                    forwardVisited.add(nodeName);
                    if(backwardVisited.contains(nodeName)) {
                        break;
                    }
                    node = nodes.get(nodeName);
                    for(String child : node.getOutEdges()) {
                        if(!forwardVisited.contains(child) && !forwardQueue.contains(child)) {
                            forwardQueue.add(child);
                            nodes.get(child).setForwardDistance(node.getForwardDistance()+1);
                        }
                    }
                }
            }

            if(!backwardQueue.isEmpty()) {
                nodeName = backwardQueue.remove();
                if(!backwardVisited.contains(nodeName)) {
                    backwardVisited.add(nodeName);
                    if(forwardVisited.contains(nodeName)) {
                        break;
                    }
                    node = nodes.get(nodeName);
                    for(String parent : node.getInEdges()) {
                        if(!backwardVisited.contains(parent) && !backwardQueue.contains(parent)) {
                            backwardQueue.add(parent);
                            nodes.get(parent).setBackwardDistance(node.getBackwardDistance()+1);
                        }
                    }
                }
            }
        }

        int shortestPathLength = node.getForwardDistance() + node.getBackwardDistance();
        resetDistances();
        if(shortestPathLength != 0) {
            return shortestPathLength;
        }
        return -1;
    }
}
