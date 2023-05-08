package Graph;
import java.util.ArrayList;

public class TestGraph {

    public static void main(String[] args) throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> batch = new ArrayList<>();
        lines.add("1 2");
        lines.add("2 3");
        lines.add("3 1");
        lines.add("4 1");
        lines.add("2 4");
        lines.add("S");
        GraphC graph = new GraphC();
        System.out.println(graph.buildTheGraph(lines));
        batch.add("Q 1 3");
        batch.add("A 4 5");
        batch.add("Q 1 5");
        batch.add("Q 5 1");
        batch.add("F");
        graph.newBatch(batch);
        batch = new ArrayList<>();
        batch.add("A 5 3");
        batch.add("Q 1 3");
        batch.add("D 2 3");
        batch.add("Q 1 3");
        batch.add("F");
        graph.newBatch(batch);
    }
}