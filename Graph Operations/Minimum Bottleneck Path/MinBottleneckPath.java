
import java.util.*;
import CustomDataStructures.Heap.MinHeap;
import CustomDataStructures.Compare.Compare;

class MinBottleneckPath
{
    MinBottleneckPath(){}

    static int[][] getMinBottleneckPath(int graph[][][], int startNode, int targetNode)
    {
	/*Finds minimum bottleneck path in O(mlogn)*/        

	int bottlenecks[] = new int[graph.length]; //The bottlenecks i.e max edge lengths in the paths to the different nodes
        MinHeap<ArrayList<Integer>> heap = generateMinHeap(graph.length, startNode, bottlenecks);
        boolean visitedNodes[] = new boolean[graph.length]; //Indicates whether a node has been visited
        int lastNodes[] = new int[graph.length]; //Keeps track of the nodes traversed before each node

        while(heap.getSize() != 0)
        {
            ArrayList<Integer> node = heap.poll(); //Getting the node with the min bottleneck
            visitedNodes[node.get(0)] = true;

            for(int a = 0; a < graph[node.get(0)].length;++a)
            {
                int edge = graph[node.get(0)][a][0];
                if(visitedNodes[edge]) continue;

                int newBottleneck = (int)Math.max(bottlenecks[node.get(0)], graph[node.get(0)][a][1]); 
                if(newBottleneck < bottlenecks[edge])
                {
                    ArrayList<Integer> oldKey = new ArrayList<Integer>();
                    oldKey.add(edge);
                    oldKey.add(bottlenecks[edge]);

                    ArrayList<Integer> newKey = new ArrayList<Integer>();
                    newKey.add(edge);
                    newKey.add(newBottleneck);

                    heap.changeKey(oldKey, newKey); //Updating the heap
                    bottlenecks[edge] = newBottleneck;
                    lastNodes[edge] = node.get(0);
                }

            }
        }

        return new int[][]{ {bottlenecks[targetNode]}, inferPath(lastNodes, targetNode)};
    }

    static int[][] getMinBottleneckPath_Linear(int graph[][][], int startNode, int targetNode)
    {
	/*Finds minimum bottleneck path in O(n+m)*/        

	int minBottlenecks[] = new int[graph.length];
        //Initializing the min bottlenecks array
        for(int a = 0; a < minBottlenecks.length;++a)
        {
            if(a == startNode) minBottlenecks[a] = 0;
            else minBottlenecks[a] = Integer.MAX_VALUE;
        }
        int lastNodes[] = new int[graph.length]; //Keeps track of the nodes traversed before each node

        for(int a = 0;a < graph.length;++a)
        {
            for(int b = 0; b < graph[a].length;++b)
            {
                int edge = graph[a][b][0];

                int newBottleneck = Math.max(minBottlenecks[a], graph[a][b][1]);
                if(newBottleneck < minBottlenecks[edge])
                {
                    minBottlenecks[edge] = newBottleneck;
                    lastNodes[edge] = a;
                }
                
            }
        }

        return new int[][]{ {minBottlenecks[targetNode]}, inferPath(lastNodes, targetNode)};
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

