package Graph;

import java.rmi.Remote;
import java.util.ArrayList;

public interface GraphI extends Remote {
    String buildTheGraph(ArrayList<String> lines) throws Exception;
    void addPathBetween(String line) throws Exception;
    void deletePathBetween(String line) throws Exception;
    int numberOfTheShortestPathBetween(String line) throws Exception;
}

