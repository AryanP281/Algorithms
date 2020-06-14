
import java.util.*;
import java.io.*;

public class Test {
     
    public static void main(String args[]) throws IOException
    {
        ArrayList<Integer> graphList[] = new ArrayList[875714];
        readGraph("Assignment.txt", graphList);
        int graph[][] = arrayListToArray(graphList);

        //int graph[][] = {{3}, {7}, {5}, {6}, {1}, {8}, {0}, {4,5}, {2,6}};

        ArrayList<ArrayList<Integer>> comps = DepthFirstSearch.getStronglyConnectedComponents(graph);
    }

    private static void readGraph(String path, ArrayList graph[]) throws IOException
    {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while((line = reader.readLine()) != null)
        {
            String edge[] = line.split("\\s");
            int start = Integer.parseInt(edge[0]);
            int end = Integer.parseInt(edge[1]);

            if(graph[start - 1] == null) graph[start-1] = new ArrayList<Integer>();
            graph[start-1].add(end-1);
        }

        reader.close();
    }

    private static int[][] arrayListToArray(ArrayList<Integer> graphList[])
    {
        int graph[][] = new int[graphList.length][];

        for(int a = 0; a < graphList.length;++a)
        {
            if(graphList[a] == null)
            {
                graph[a] = new int[1];
                graph[a][0] = a;
            }
            else
            {
                graph[a] = new int[graphList[a].size()];
                for(int b = 0; b < graph[a].length;++b)
                {
                    graph[a][b] = graphList[a].get(b);
                }
            }
        }

        return graph;
    }
}