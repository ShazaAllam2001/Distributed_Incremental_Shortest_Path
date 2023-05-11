package Graph;

public interface GraphI {
    /**
     * add edge from source to destination
     * @param source starting node name
     * @param destination ending node name
     **/
    void addEdge(String source, String destination);

    /**
     * delete edge from source to destination
     * @param source starting node name
     * @param destination ending node name
     **/
    void deleteEdge(String source, String destination);

    /**
     * get the shortest path from source to destination using BFS
     * @param source starting node name
     * @param destination ending node name
     **/
    int getShortestPath1(String source, String destination);

    /**
     * get the shortest path from source to destination using Bidirectional Search
     * @param source starting node name
     * @param destination ending node name
     **/
    int getShortestPath2(String source, String destination);
}
