package Graph;

public class TestGraph {
    public static void main(String[] args) {
        Graph obj = new Graph();
        obj.addEdge("0","1");
        obj.addEdge("0","4");
        obj.addEdge("0","2");
        obj.addEdge("2","1");
        obj.addEdge("1","3");
        obj.addEdge("3","4");
        obj.addEdge("4","2");
        obj.addEdge("4","5");
        obj.addEdge("4","6");
        obj.addEdge("3","6");
        System.out.println(obj.findShortestPath("0","4"));
    }
}
