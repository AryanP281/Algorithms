
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class Test {
    
    public static void main(String args[]) throws IOException
    {
        int graph[][][] = null;
        KruzkalMst kruz = null;
        for(byte a = 0; a < 2; ++a)
        {
            graph = readFromFile("input0" + a + ".txt");
            kruz = new KruzkalMst(graph);
            ArrayList<Edge> mst = kruz.getMst();
            int cost = getTotalCost(mst);
            System.out.println(cost);
        }

    }

    private static int[][][] readFromFile(String file) throws IOException
    {

        BufferedReader br = new BufferedReader(new FileReader(new File(file)));

        ArrayList<int[]> graphList[] = new ArrayList[Integer.parseInt((br.readLine().split("\\s"))[0])];
        String line = null;
        while((line = br.readLine()) != null)
        {
            String data[] = line.split("\\s");
            if(graphList[Integer.parseInt(data[0]) - 1] == null) graphList[Integer.parseInt(data[0]) - 1] = new ArrayList<int[]>();
            graphList[Integer.parseInt(data[0]) - 1].add(new int[]{Integer.parseInt(data[1]) - 1, Integer.parseInt(data[2])});
            if (graphList[Integer.parseInt(data[1]) - 1] == null) graphList[Integer.parseInt(data[1]) - 1] = new ArrayList<int[]>();
            graphList[Integer.parseInt(data[1]) - 1].add(new int[]{Integer.parseInt(data[0]) - 1, Integer.parseInt(data[2])});
        }

        br.close();

        int graph[][][] = new int[graphList.length][][];
        for(int a = 0; a < graph.length; ++a)
        {
           graph[a] = new int[graphList[a].size()][];
            for(int b = 0;b < graphList[a].size(); ++b)
           {
               graph[a][b] = graphList[a].get(b);
           }
        }

        return graph;
    }

    private static int getTotalCost(ArrayList<Edge> mst)
    {
        int cost = 0;

        for(int a = 0; a < mst.size(); ++a)
        {
            cost += mst.get(a).length;
        }

        return cost;
    }
}

/*int graph[][][] = { {{1,1}, {2,7}}, {{0,1}, {2,5}, {3,4}, {4,3}}, {{0,7}, {1,5}, {4,6}}, {{1,4}, {4,2}}, {{1,3}, {2,6}, {3,2}} };
    
        KruzkalMst kmst = new KruzkalMst(graph);
        ArrayList<Edge> mst = kmst.getMst();*/