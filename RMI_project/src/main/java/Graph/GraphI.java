package Graph;
import java.rmi.Remote;
import java.util.ArrayList;

public interface GraphI extends Remote {

    String buildTheGraph(ArrayList<String> lines) throws Exception;
    void newBatch(ArrayList<String> batch) throws Exception;
}