
import java.util.*;

import CustomDataStructures.Heap.*;
import CustomDataStructures.Compare.Compare;

class PrimsMst
{
    private static Compare<ArrayList<Integer>> cmp = new Compare<ArrayList<Integer>>()
    {
        @Override
        public int compare(ArrayList<Integer> t1, ArrayList<Integer> t2)
        {
            return Integer.compare(t1.get(1), t2.get(1));
        }

    }; //The interface to be used by the heap for comparisons

    static ArrayList<Edge> getMst(int graph[][][], Integer startingVertex)
    {
        /*Finds and returns the mst*/

        //Selecting the starting vertex
        int start = startingVertex == null ? randomInRange(0, graph.length) : startingVertex;
        Edge minEdges[] = new Edge[graph.length]; //The min cost edges for the different vertices
        boolean visitedVertices[] = new boolean[graph.length]; //Marks the vertices that have been added to the tree
        visitedVertices[start] = true; //Marking the starting node as visited
        ArrayList<Edge> mst = new ArrayList<Edge>(); //The mst

        //Initializing the heap
        MinHeap<ArrayList<Integer>> heap = new MinHeap<ArrayList<Integer>>(graph.length, cmp, arrayToList(new int[]{0, Integer.MIN_VALUE}));
        //Building the heap
        for(int a = 0; a < graph.length; ++a)
        {
            if(a == start) continue;
            
            int hasEdge = hasDirectEdge(start, a, graph);
            if(hasEdge != -1)
            {
                heap.push(arrayToList(new int[]{a, graph[start][hasEdge][1]}));
                minEdges[a] = new Edge(start, a, graph[start][hasEdge][1]);
            }
            else
            {
                heap.push(arrayToList(new int[]{a, Integer.MAX_VALUE}));
                minEdges[a] = new Edge(a,a,Integer.MAX_VALUE);
            }
        }

        //Finding the mst
        while(heap.getSize() != 0)
        {
            int newVertex = heap.poll().get(0); //The new min cost vertex to be added to the tree
            visitedVertices[newVertex] = true; //Marking the vertex as visited
            mst.add(minEdges[newVertex]); //Adding the vertex to the tree

            //Updating the costs of the connected vertices
            for(int a = 0; a < graph[newVertex].length; ++a)
            {
                if(visitedVertices[graph[newVertex][a][0]]) continue;

                int newCost = graph[newVertex][a][1];
                if(newCost < minEdges[graph[newVertex][a][0]].length)
                {
                    heap.changeKey(arrayToList(new int[]{graph[newVertex][a][0], minEdges[graph[newVertex][a][0]].length}), 
                    arrayToList(new int[]{graph[newVertex][a][0], newCost})); //Updating the vertex's cost in the heap

                    //Updating the min edge
                    minEdges[graph[newVertex][a][0]].startVertex = newVertex;
                    minEdges[graph[newVertex][a][0]].endVertex = graph[newVertex][a][0];
                    minEdges[graph[newVertex][a][0]].length = newCost;
                }

            }

        }

        return mst;
    }

    private static int randomInRange(int start, int end)
    {
        /*Returns a random number in the range [start, end) */
        
        return (int)(start + (Math.random() * (end - start)));
    }

    private static int hasDirectEdge(int edgeFrom, int edgeTo, int graph[][][])
    {
        /*Returns whether there is a direct edge between the given two vertices*/

        for(int a = 0; a < graph[edgeFrom].length;++a)
        {
            if(graph[edgeFrom][a][0] == edgeTo) return a;
        }

        return -1;
    }

    private static ArrayList<Integer> arrayToList(int list[])
    {
        /*Returns an ArrayList containing the elements in the given array*/
        
        ArrayList<Integer> al = new ArrayList<Integer>();
        for(int e : list)
        {
            al.add(e);
        }

        return al;
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