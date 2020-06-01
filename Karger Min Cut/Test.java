import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

public class Test {
    
    public static void main(String args[]) throws IOException
    {
        ArrayList<ArrayList<Integer>> graph = readGraph("Assignment.txt");

        MinCut mc = new MinCut();
        int minCut = mc.getMinCut(graph);

    }

    private static ArrayList<ArrayList<Integer>> readGraph(String path) throws IOException
    {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();

        File graphFile = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(graphFile));

        String line = "";
        while((line = reader.readLine()) != null)
        {
            Integer edges[] = getEdges(line);
            graph.add(new ArrayList<Integer>(Arrays.asList(edges)));
        }

        reader.close();
        return graph;
    }

    private static Integer[] getEdges(String line)
    {
        String str_edges[] = line.split("\\s");
        Integer edges[] = new Integer[str_edges.length-1];
        for(int a = 0; a < edges.length; ++a)
        {
            edges[a] = Integer.parseInt(str_edges[a+1]) - 1;
        }

        return edges;
    }

}