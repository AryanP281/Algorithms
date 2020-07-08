
import java.util.*;
import CustomDataStructures.Compare.Compare;

class KruzkalMst
{
    Edge graph[]; //The graph represented in the form of an arraylist of edges
    private int numVertices; //The number of vertices in the graph

    private Compare<Edge> compareEdges = new Compare<Edge>()
    {
        @Override
        public int compare(Edge e1, Edge e2)
        {
            return Integer.compare(e1.length, e2.length);
        }
    }; //Interface for comparing the edges

    KruzkalMst(int adjacencyList[][][])
    {
        /*Initializes the model*/

        numVertices = adjacencyList.length;

        //Preparing a graph from the adjacency list
        ArrayList<Edge> graphList = new ArrayList<Edge>();
        for(int a = 0; a < adjacencyList.length; ++a)
        {
            for(int edge[] : adjacencyList[a])
            {
                graphList.add(new Edge(a, edge[0], edge[1]));
            }
        }

        //Converting list to array
        graph = graphList.toArray(new Edge[graphList.size()]);
    }

    ArrayList<Edge> getMst()
    {
        /*Returns the mst of the given graph*/

        ArrayList<Edge> mst = new ArrayList<Edge>(); //The mst of the given graph
        int leaders[] = new int[numVertices]; //The leader of the set for each vertex
        HashMap<Integer, Integer> setSizes = new HashMap<Integer, Integer>(); //The set sizes mapped according to the set leaders
        for(int a = 0; a < leaders.length;++a)
        {
            leaders[a] = a;
            setSizes.put(a, 1);
        }

        QuickSort<Edge> sort = new QuickSort<Edge>(compareEdges); //Sorts the edges
        sort.sort(graph, sort.randomPivot); //Sorting the edges according to their lengths


        for(int a = 0; a < graph.length;++a)
        {
            Edge edge = graph[a];
            //Checking if adding the edge creates a cycle
            if(formsCycle(edge, leaders)) continue;

            //Adding the edge to the mst
            mst.add(edge);

            //Updating the sets
            updateSets(edge, leaders, setSizes);
        }
        

        return mst;
    }

    private boolean formsCycle(Edge edge, int leaders[])
    {
        /*Tells whether adding the given edge will form a cycle*/

        return leaders[edge.startVertex] == leaders[edge.endVertex];
    }

    private void updateSets(Edge edge, int leaders[], HashMap<Integer,Integer> setSizes)
    {
        /*Merges the two sets*/

        int leaderToChange = setSizes.get(leaders[edge.startVertex]) < setSizes.get(leaders[edge.endVertex]) ? leaders[edge.startVertex] :
            leaders[edge.endVertex]; //Getting the leader of the smaller set
        int newLeader = leaderToChange == leaders[edge.startVertex] ? leaders[edge.endVertex] : leaders[edge.startVertex];

        //Updating the leader of the vertices in the smaller set
        for(int a = 0; a < leaders.length; ++a)
        {
            if(leaders[a] == leaderToChange) leaders[a] = newLeader;
        }

        //Updating the set sizes
        setSizes.put(newLeader, setSizes.get(newLeader) + setSizes.get(leaderToChange));
        setSizes.remove(leaderToChange);
    }
}

class Edge
{
    int startVertex;
    int endVertex;
    int length;

    Edge(int start, int end, int len)
    {
        startVertex = start;
        endVertex = end;
        length = len;
    }
}