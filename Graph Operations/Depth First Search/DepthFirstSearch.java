
import java.util.*;
import CustomDataStructures.Stack.Stack;

class IntegerReference
{
    int val;

    IntegerReference(int x) {val = x;}
}

class DepthFirstSearch
{
    private static int topologicalNodeIndex; //The index of the current node in topological order
    
    DepthFirstSearch(){}

    static boolean connected(int graph[][], int start, int target)
    {
        /*Checks and returns whether the start and target node are connected
        graph = the adjacency list
        start = the start node
        target = the target node to be checked for connectivity
        */

        boolean visitedNodes[] = new boolean[graph.length]; //Tells whether a node has been visited

        return dfs(graph, start, target, visitedNodes);
    }

    private static boolean dfs(int graph[][], int start, int target, boolean visitedNodes[])
    {
        /*Checks and returns whether the start and target node are connected using Depth-First-Search*/

        if(start == target) return true; //Checks whether the target node has been found
        
        visitedNodes[start] = true; //Marking the node as visited
        int edges[] = graph[start]; //Getting the nodes connected to the start node
        for(int node : edges)
        {
            if(visitedNodes[node]) continue;

            boolean targetFound = dfs(graph, node, target, visitedNodes); //Recursing down the path
            if(targetFound) return true;
        }

        return false;
    }

    static int[] getTopologicalOrder(int graph[][])
    {
        /*Returns the topological ordering of a acyclic directed graph
        graph = the adjacency list*/

        int orders[] = new int[graph.length];
        topologicalNodeIndex = graph.length-1; //Initializing the topological index counter to length to graph i.e n
        boolean visitedNodes[] = new boolean[graph.length];

        //Getting the topological ordering
        for(int a = 0; a < graph.length;++a)
        {
            if(visitedNodes[a]) continue;
            topologicalOrder(graph, a, orders, visitedNodes);
        }

        return orders;
    }

    private static void topologicalOrder(int graph[][], int currentNode, int orders[], boolean visitedNodes[])
    {
        if(visitedNodes[currentNode]) return;

        visitedNodes[currentNode] = true; //Marking the current node as visited
        int edges[] = graph[currentNode]; //Getting the nodes connected to the current node
        for(int node : edges)
        {
            if(visitedNodes[node]) continue;
            topologicalOrder(graph, node, orders, visitedNodes); //Calculating the topological order using dfs
        }
        orders[topologicalNodeIndex] = currentNode; //Placing the current node in the topological order 
        --topologicalNodeIndex;
    }
    
    static ArrayList<ArrayList<Integer>> getStronglyConnectedComponents(int graph[][])
    {
        /*Returns the strongly connected components of the graph
        graph = the adjacency list
        */

        ArrayList<Integer> reverseGraph[] = getReverseGraph(graph); //Getting the reverse graph

        //Running the 1st dfs loop to calculate the finishing times
        int finishingTimes[] = new int[graph.length]; //The nodes sorted by finishing times
        boolean visitedNodes[] = new boolean[graph.length]; //The nodes visited
        IntegerReference t = new IntegerReference(0); //The finishing time of the last node
        for(int a = graph.length-1;a>=0;--a)
        {
            if(visitedNodes[a]) continue;
            
            if(graph.length >= 1000)
                calculateFinishingTimes_iterative(reverseGraph, a, visitedNodes, finishingTimes, t);
            else
                calculateFinishingTimes(reverseGraph, a, visitedNodes, finishingTimes, t); 
        }

        //Finding the strongly connected components
        visitedNodes = new boolean[graph.length]; //Resetting the visited flags
        ArrayList<ArrayList<Integer>> comps = new ArrayList<ArrayList<Integer>>();
        for(int a = finishingTimes.length-1; a >= 0;--a)
        {
            if(visitedNodes[finishingTimes[a]]) continue;

            comps.add(new ArrayList<Integer>());
            comps.get(comps.size()-1).add(finishingTimes[a]);
            if(graph.length >= 100)
                findStronglyConnectedComponent_iterative(graph, finishingTimes[a], visitedNodes, comps.get(comps.size()-1));
            else
                findStronglyConnectedComponent(graph, finishingTimes[a], visitedNodes, comps.get(comps.size()-1));
        
            }

        return comps;
    }

    private static ArrayList<Integer>[] getReverseGraph(int graph[][])
    {
        ArrayList<Integer> rev[] = new ArrayList[graph.length];
        for(int node = 0; node < graph.length;++node)
        {
            for(int edge : graph[node])
            {
                if(rev[edge] == null) rev[edge] = new ArrayList<Integer>();
                rev[edge].add(node);
            }
        }

        //Pointing null nodes to themselves
        for(int a = 0; a < rev.length;++a)
        {
            if(rev[a] == null)
            {
                rev[a] = new ArrayList<Integer>();
                rev[a].add(a);
            }
        }

        return rev;
    }

    private static void calculateFinishingTimes(ArrayList<Integer> revGraph[], int startNode, boolean visitedNodes[], int finTimes[], IntegerReference t)
    {
        /*Calculates the finishing times of the nodes in the graphs
        revGraph = adjacency list of the reverse graph
        startNode = the node to start the calculation from
        visitedNodes = tells whether a nodes has been already visited
        finTimes = the finishing times
        t = the finishing time of the last node
        */

        visitedNodes[startNode] = true; //Marking the node as visited
        ArrayList<Integer> edges = revGraph[startNode]; //Getting the outgoing edges of the node
        if(edges != null)
        {
            for(Integer edge : edges)
            {
                if(visitedNodes[edge]) continue;

                calculateFinishingTimes(revGraph, edge, visitedNodes, finTimes, t); //Traversing the edge
            }
        }
        finTimes[(t.val)++] = startNode; //Placing the current node according to its finishing time

    }

    private static void calculateFinishingTimes_iterative(ArrayList<Integer> revGraph[], int startNode, boolean visitedNodes[], int finTimes[], IntegerReference t)
    {
        /*Calculates the finishing times, without using recursion
        To be used when data size is too large for recursive search.
        revGraph = adjacency list of the reverse graph
        startNode = the node to start the calculation from
        visitedNodes = tells whether a nodes has been already visited
        finTimes = the finishing times
        t = the finishing time of the last node
        */

        Stack<Integer> nodeStack = new Stack<Integer>(revGraph.length); //Stack containing the nodes to be visited
        Stack<Integer> nodeOrder = new Stack<Integer>(revGraph.length); //The order in which the nodes were visited
        visitedNodes[startNode] = true; //Marking the start node as visited
        nodeStack.push(startNode); //Adding the start node to the 
        nodeOrder.push(startNode);

        while(!nodeStack.isEmpty())
        {
            int node = nodeStack.pop();

            ArrayList<Integer> edges = revGraph[node];
            for(Integer edge : edges)
            {
                if(visitedNodes[edge]) continue;

                visitedNodes[edge] = true; //Marking the edge as visited
                nodeStack.push(edge);
                nodeOrder.push(edge);
            }
            
        }

        //Sorting the nodes by finishing times
        while(!nodeOrder.isEmpty())
        {
            finTimes[(t.val)++] = nodeOrder.pop();
        }

    }


    private static void findStronglyConnectedComponent(int graph[][], int startNode, boolean visitedNodes[], ArrayList<Integer> comp)
    {
        /*Traverses and discovers the nodes in the strongly connected component containing the start node
        graph = adjacency list
        startNode = the node to start traversing
        visitedNodes = tells whether a nodes has been already visited
        comp = list of the nodes in the strongly connected component
        */

        visitedNodes[startNode] = true; //Marking the node as visisted
        int edges[] = graph[startNode]; //Getting the connected edges
        for(int edge : edges)
        {
            if(visitedNodes[edge]) continue;

            comp.add(edge); //Adding the node to the component
            findStronglyConnectedComponent(graph, edge, visitedNodes, comp); //Traversing the edge
        }

    }

    private static void findStronglyConnectedComponent_iterative(int graph[][], int startNode, boolean visitedNodes[], ArrayList<Integer> comp)
    {
        /*Traverses and discovers the nodes in the strongly connected component containing the start node. 
        To be used when data size is too large for recursive search.
        graph = adjacency list
        startNode = the node to start traversing
        visitedNodes = tells whether a nodes has been already visited
        comp = list of the nodes in the strongly connected component
        */

        Stack<Integer> nodeStack = new Stack<Integer>(graph.length); //Stack containing the nodes to be visited
        visitedNodes[startNode] = true; //Marking the the start node
        nodeStack.push(startNode);

        while(!nodeStack.isEmpty())
        {
            int currentNode = nodeStack.pop();
            int edges[] = graph[currentNode];

            for(int edge : edges)
            {
                if(visitedNodes[edge]) continue;

                visitedNodes[edge] = true; //Marking the node as visited
                nodeStack.push(edge); //Adding the node to the stack
                comp.add(edge); //Adding the node to the component
            }
        }

    }
}