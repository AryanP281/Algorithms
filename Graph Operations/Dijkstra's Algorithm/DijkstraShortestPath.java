
import java.util.*;
import CustomDataStructures.Heap.MinHeap;
import CustomDataStructures.Compare.*;

class DijkstraShortestPath
{
    DijkstraShortestPath(){}

    static int[][] getShortestPath(int graph[][][], int startNode, int targetNode)
    {
        //Creating the heap
        int minGreedyScores[] = new int[graph.length]; //The min greedy scores for the nodes
        MinHeap<ArrayList<Integer>> heap = generateMinHeap(graph.length, startNode, minGreedyScores); //Generating the heap
        boolean visitedNodes[] = new boolean[graph.length]; //Indicates whether a node has been visited
        int lastNodes[] = new int[graph.length]; //Keeps track of the last 

        while(heap.getSize() != 0)
        {
            //Getting the node having the min Dijkstra Greedy Score
            ArrayList<Integer> node = heap.poll();
            visitedNodes[node.get(0)] = true; //Marking the node as visited

            //Updating the nodes connected to the current node
            for(int a = 0; a < graph[node.get(0)].length; ++a)
            {
                if(visitedNodes[graph[node.get(0)][a][0]]) continue;

                int newScore = node.get(1) + graph[node.get(0)][a][1];
                //Checking if the new greedy score is lower than old greedy score
                if(newScore < minGreedyScores[graph[node.get(0)][a][0]])
                {
                    ArrayList<Integer> oldKey = new ArrayList<Integer>();
                    oldKey.add(graph[node.get(0)][a][0]);
                    oldKey.add(minGreedyScores[graph[node.get(0)][a][0]]);

                    ArrayList<Integer> newKey = new ArrayList<Integer>();
                    newKey.add(graph[node.get(0)][a][0]);
                    newKey.add(newScore);
                    
                    heap.changeKey(oldKey, newKey); //Updating the heap

                    minGreedyScores[graph[node.get(0)][a][0]] = newScore;
                    lastNodes[graph[node.get(0)][a][0]] = node.get(0);
                }
            }
        }

        return new int[][]{{minGreedyScores[targetNode]}, inferPath(lastNodes, targetNode)};
    }

    private static MinHeap<ArrayList<Integer>> generateMinHeap(int numNodes, int startNode, int[] scoresTrack)
    {
        //The interface for comparing elements
        Compare<ArrayList<Integer>> cmp = new Compare<ArrayList<Integer>>()
        {
            @Override
            public int compare(ArrayList<Integer> t1, ArrayList<Integer> t2)
            {
                if(t1.get(1) == t2.get(1)) return 0;
                
                return (t1.get(1) > t2.get(1)) ? 1 : -1;
            }
        };
        
        //Initializing the heap
        ArrayList<Integer> minLimit = new ArrayList<Integer>();
        minLimit.add(0);
        minLimit.add(Integer.MIN_VALUE);
        MinHeap<ArrayList<Integer>> heap = new MinHeap<ArrayList<Integer>>(numNodes, cmp, minLimit);
        
        //Adding the nodes to the heap
        for(int a = 0; a < numNodes;++a)
        {
            ArrayList<Integer> node = new ArrayList<Integer>();
            if(a == startNode)
            {
                node.add(a);
                node.add(0);
                heap.push(node);
                scoresTrack[a] = 0;
            }
            else
            {
                node.add(a);
                node.add(Integer.MAX_VALUE);
                heap.push(node);
                scoresTrack[a] = Integer.MAX_VALUE;
            }
        }

        return heap;
    }

    private static int[] inferPath(int lastNodes[], int targetNode)
    {
        ArrayList<Integer> revPath = new ArrayList<Integer>(); //The path starting from target till source
        revPath.add(targetNode);
        
        int a = targetNode;
        while(lastNodes[a] != a)
        {
            revPath.add(lastNodes[a]);
            a = lastNodes[a];
        }

        int path[] = new int[revPath.size()]; //The actual path
        for(int b = revPath.size()-1; b >= 0; --b)
        {
            path[revPath.size()-1-b] = revPath.get(b);
        }

        return path;
    }
}