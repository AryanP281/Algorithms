
import java.util.*;
import CustomDataStructures.Queue.Queue;

class BreadthFirstSearch
{

    BreadthFirstSearch(){}

    static int getMinDistance(int graph[][], int start, int target)
    {
        /*Returns the min distance between the start and target nodes
        graph = the adjacency list
        start = the node to start the search from
        target = the target node to be reached */

        Queue<Integer> nodeQueue = new Queue<Integer>(graph.length); //A queue containing the nodes to be explored
        boolean visitedNodes[] = new boolean[graph.length]; //Tells which nodes have already been visited
        int distances[] = new int[graph.length]; //The distances of the connected nodes from the start node

        //Initializing
        nodeQueue.enqueue(start); //Adding the starting node to the queue of nodes to be visited
        visitedNodes[start] = true; //Setting the starting node as visited
        distances[start] = 0; //The distance of the start node from itself is 0

        while(nodeQueue.getSize() != 0)
        {
            int currentNode = nodeQueue.dequeue(); //The node currently traversed
            int edges[] = graph[currentNode]; //Getting the edges connected to the node at the front of the queue
            for(int node : edges)
            {
                if(visitedNodes[node]) continue;

                if(node == target) return distances[currentNode] + 1;
                
                visitedNodes[node] = true; //Marking the node as visited
                nodeQueue.enqueue(node); //Adding the node to the queue of nodes to be visited
                distances[node] = distances[currentNode] + 1;
            }
        }

        return -1;
    }

    static boolean connected(int graph[][], int start, int target)
    {
        /*Tells whether the start and target node are connected
        graph = the adjacency list
        start = the node to start the search from
        target = the target node to be reached
        */

        Queue<Integer> nodeQueue = new Queue<Integer>(graph.length); //A queue containing the nodes to be explored
        boolean visitedNodes[] = new boolean[graph.length]; //Tells which nodes have already been visited

        //Initializing
        nodeQueue.enqueue(start); //Adding the starting node to the queue of nodes to be visited
        visitedNodes[start] = true; //Setting the starting node as visited

        while(nodeQueue.getSize() != 0)
        {
            int edges[] = graph[nodeQueue.dequeue()]; //Getting the edges connected to the node at the front of the queue
            for(int node : edges)
            {
                if(visitedNodes[node]) continue;
                
                if(node == target) return true;
                visitedNodes[node] = true; //Marking the node as visited
                nodeQueue.enqueue(node); //Adding the node to the queue of nodes to be visited
            }
        }

        return false;

    }

    static ArrayList<ArrayList<Integer>> getConnectedComponents(int graph[][])
    {
        /*Finds and returns the connected components in the graph
        graph = the adjacency list */

        Queue<Integer> nodeQueue = new Queue<Integer>(graph.length); //A queue containing the nodes to be explored
        boolean visitedNodes[] = new boolean[graph.length]; //Tells which nodes have already been visited
        ArrayList<ArrayList<Integer>> connectedComps = new ArrayList<ArrayList<Integer>>(); //The connected components in the graph

        for(int a = 0; a < graph.length;++a)
        {
            if(visitedNodes[a]) continue;

            connectedComps.add(new ArrayList<Integer>()); //Creating a new connected component
            connectedComps.get(connectedComps.size()-1).add(a); //Adding the starting node

            visitedNodes[a] = true; //Marking the node as visited
            nodeQueue.enqueue(a);
            while(nodeQueue.getSize() != 0)
            {
                int edges[] = graph[nodeQueue.dequeue()]; //Getting the edges connected to the node at the front of the queue
                for(int node : edges)
                {
                    if(visitedNodes[node]) continue;
                    
                    visitedNodes[node] = true; //Marking the node as visited
                    nodeQueue.enqueue(node); //Adding the node to the queue of nodes to be visited
                    connectedComps.get(connectedComps.size()-1).add(node); //Adding the node to the connected component
                }
            }
        }

        return connectedComps;
    }
}