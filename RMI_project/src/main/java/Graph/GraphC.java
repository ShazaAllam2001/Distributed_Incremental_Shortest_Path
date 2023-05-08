package Graph;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class GraphC extends UnicastRemoteObject implements GraphI {

    private final HashMap<String, MyNode> nodes = new HashMap<>();
    private final Queue<ArrayList<String>> batches = new LinkedList<>();
    private HashMap<String, String> myParent = new HashMap<>();
    private HashMap<String, String> myChild = new HashMap<>();

    protected GraphC() throws Exception {
        super();
    }

    public String buildTheGraph(ArrayList<String> lines) {
        for (String line : lines) {
            if (line.equals("S")) {
                break;
            }
            addPathBetween(line);
        }
        serverThread.start();
        return "R";
    }
    public void newBatch(ArrayList<String> batch) {
        batches.add(batch);
    }
    private void addPathBetween(String line) {
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
            node = nodes.get(nodeNames[0]);
            node.addNodeToCanGoTo(nodeNames[1]);
            nodes.replace(nodeNames[0], node);
            node = nodes.get(nodeNames[1]);
            node.addNodeToCanGetReachToFrom(nodeNames[0]);
            nodes.replace(nodeNames[1], node);
        }
    }
    private void deletePathBetween(String line) {
        String[] nodeNames = line.split(" ");
        MyNode node;
        if (nodes.containsKey(nodeNames[0])) {
            node = nodes.get(nodeNames[0]);
            node.deleteNodeFromCanGoTo(nodeNames[1]);
            nodes.replace(nodeNames[0], node);
        }
        if (nodes.containsKey(nodeNames[1])) {
            node = nodes.get(nodeNames[1]);
            node.deleteNodeFromCanGetReachToFrom(nodeNames[0]);
            nodes.replace(nodeNames[1], node);
        }
    }
    private int numberOfTheShortestPathBetween(String line) {
        String[] nodeNames = line.split(" ");
        MyNode start = nodes.get(nodeNames[0]);
        MyNode goal = nodes.get(nodeNames[1]);
        return bidirectionalSearch(start, goal);
        //return BFS(start, goal);
    }
    private int bidirectionalSearch(MyNode start, MyNode goal) {
        if (!nodes.containsKey(start.getNodeName())
                || !nodes.containsKey(goal.getNodeName())) {
            return -1;
        }
        if (start.getNodeName().equals(goal.getNodeName())) {
            return 0;
        }
        Queue<String> forwardQueue = new LinkedList<>();
        ArrayList<String> forwardVisited = new ArrayList<>();
        Queue<String> backwardQueue = new LinkedList<>();
        ArrayList<String> backwardVisited = new ArrayList<>();
        forwardQueue.add(start.getNodeName());
        backwardQueue.add(goal.getNodeName());
        String middleNode = "";
        String nodeName;
        MyNode node;
        while (!forwardQueue.isEmpty() || !backwardQueue.isEmpty()) {
            if (!forwardQueue.isEmpty()) {
                nodeName = forwardQueue.remove();
                if (!forwardVisited.contains(nodeName)) {
                    forwardVisited.add(nodeName);
                    if (backwardVisited.contains(nodeName)) {
                        middleNode = nodeName;
                        break;
                    }
                    node = nodes.get(nodeName);
                    for (String child : node.getCanGoTo()) {
                        if (!forwardVisited.contains(child)) {
                            forwardQueue.add(child);
                            myParent.put(child, node.getNodeName());
                        }
                    }
                }
            }
            if (!backwardQueue.isEmpty()) {
                nodeName = backwardQueue.remove();
                if (!backwardVisited.contains(nodeName)) {
                    backwardVisited.add(nodeName);
                    if (forwardVisited.contains(nodeName)) {
                        middleNode = nodeName;
                        break;
                    }
                    node = nodes.get(nodeName);
                    for (String parent : node.getCanGetReachToFrom()) {
                        if (!backwardVisited.contains(parent)) {
                            backwardQueue.add(parent);
                            myChild.put(parent, node.getNodeName());
                        }
                    }
                }
            }
        }
        int counter = 0;
        String child = middleNode;
        while (myParent.containsKey(child)) {
            child = myParent.get(child);
            counter++;
        }
        String parent = middleNode;
        while (myChild.containsKey(parent)) {
            parent = myChild.get(parent);
            counter++;
        }
        myParent = new HashMap<>();
        myChild = new HashMap<>();
        if (counter != 0) {
            return counter;
        }
        return -1;
    }
    private int BFS(MyNode start, MyNode goal) {
        if (!nodes.containsKey(start.getNodeName())
                || !nodes.containsKey(goal.getNodeName())) {
            return -1;
        }
        if (start.getNodeName().equals(goal.getNodeName())) {
            return 0;
        }
        Queue<String> queue = new LinkedList<>();
        ArrayList<String> visited = new ArrayList<>();
        queue.add(start.getNodeName());
        while (!queue.isEmpty()) {
            String nodeName = queue.remove();
            if (visited.contains(nodeName)) {
                continue;
            }
            visited.add(nodeName);
            MyNode node = nodes.get(nodeName);
            if (node.getNodeName().equals(goal.getNodeName())) {
                break;
            }
            for (String child : node.getCanGoTo()) {
                if (!visited.contains(child)) {
                    queue.add(child);
                    myParent.put(child, node.getNodeName());
                }
            }
        }
        int counter = 0;
        String child = goal.getNodeName();
        while (myParent.containsKey(child)) {
            child = myParent.get(child);
            counter++;
        }
        myParent = new HashMap<>();
        if (counter != 0) {
            return counter;
        }
        return -1;
    }
    private final Thread serverThread = new Thread(() -> {
        ArrayList<String> batch;
        while (true) {
            if (!batches.isEmpty()) {
                ArrayList<Integer> shortestPath = new ArrayList<>();
                batch = batches.remove();
                for (String line : batch) {
                    char operation = line.charAt(0);
                    if (operation == 'F') {
                        break;
                    }
                    line = line.substring(2);
                    if (operation == 'A') {
                        addPathBetween(line);
                    } else if (operation == 'D') {
                        deletePathBetween(line);
                    } else if (operation == 'Q') {
                        shortestPath.add(numberOfTheShortestPathBetween(line));
                    }
                }
                System.out.println(shortestPath);
                // ToDo send shortestPath to the client somehow
            }
        }
    });
}