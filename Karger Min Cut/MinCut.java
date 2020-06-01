
import java.util.*;

class MinCut
{
    MinCut(){}

    int getMinCut(ArrayList<ArrayList<Integer>> graph)
    {
        int iterations = (int)((double)graph.size() * Math.log(graph.size()));

        int min = -1;
        ArrayList<ArrayList<Integer>> minCut = null;
        for(int a = 0; a < iterations; ++a)
        {
            ArrayList<ArrayList<Integer>> cut = minCut(graph);
            int cutNode = getCutNodeIndex(cut);
            if(min == -1 || min > cut.get(cutNode).size())
            {
                min = cut.get(cutNode).size();
                minCut = cut;
            }
        }

        return min;
    }

    private ArrayList<ArrayList<Integer>> minCut(ArrayList<ArrayList<Integer>> graph)
    {
        //Making a clone of the graph to represent the min-cut configuration
        ArrayList<ArrayList<Integer>> minCut = getGraphClone(graph); //Making a clone of the graph to represent 
        
        //Contracting untill only 2 nodes are left
        while(getGraphSize(minCut) > 2)
        {
            int[] edge = getEdge(minCut); //Gets the edge to contract
            contract(minCut, edge); //Contracting the graph
        }

        return minCut;
    }

    private int[] getEdge(ArrayList<ArrayList<Integer>> minCut)
    {
        int v1 = 0;
        while(true)
        {
            v1 = randomInRange(0, minCut.size()-1);
            if(minCut.get(v1) == null) continue;
            break;
        }
        
        return new int[]{v1, randomInRange(0, minCut.get(v1).size()-1)};
    }

    private int randomInRange(int min, int max)
    {
        return (int)(min + (Math.random() * (double)((max-min)+1)));
    }

    private void contract(ArrayList<ArrayList<Integer>> minCut, int edge[])
    {
        int vertex2 = minCut.get(edge[0]).get(edge[1]);
        
        //Removing self loop
        for(int a = 0; a < minCut.get(edge[0]).size();++a)
        {
            if(minCut.get(edge[0]).get(a) == vertex2) 
            {
                minCut.get(edge[0]).remove(a);
                --a;
            }
        }

        //Adding the connections of the 2nd vertex to the group
        for(int a = 0; a < minCut.get(vertex2).size(); ++a)
        {
            if(minCut.get(vertex2).get(a) != edge[0]) minCut.get(edge[0]).add(minCut.get(vertex2).get(a));
        }

        //Rerouting all the other edges
        for(int a = 0; a < minCut.size(); ++a)
        {
            if(a == edge[0] || a == vertex2 || minCut.get(a) == null) continue;
            for(int b = 0; b < minCut.get(a).size(); ++b)
            {
                if(minCut.get(a).get(b) == vertex2) minCut.get(a).set(b, edge[0]);
            }
        }

        minCut.set(vertex2, null);
    }

    private int getGraphSize(ArrayList<ArrayList<Integer>> graph)
    {
        int ct = 0;
        for(ArrayList<Integer> node : graph)
        {
            if(node != null) ++ct;
        }

        return ct;
    }

    private int getCutNodeIndex(ArrayList<ArrayList<Integer>> cut)
    {
        for(int a = 0; a < cut.size(); ++a)
        {
            if(cut.get(a) != null) return a;
        }

        return -1;
    }

    private ArrayList<ArrayList<Integer>> getGraphClone(ArrayList<ArrayList<Integer>> graph)
    {
        ArrayList<ArrayList<Integer>> clone = new ArrayList<ArrayList<Integer>>();
        for(int a = 0; a < graph.size();++a)
        {
            clone.add((ArrayList<Integer>)graph.get(a).clone());
        }

        return clone;
    }

}