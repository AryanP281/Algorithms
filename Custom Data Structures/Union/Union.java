package CustomDataStructures.Union;

import CustomDataStructures.Compare.Compare;
import java.util.*;

public class Union<T> 
{
    
    private T parent[]; //The parents of the nodes
    private HashMap<T,Integer> arrayPositions; //The positions of the elements' parents in the parent array
    private HashMap<T,Integer> rootRanks; //The ranks of the roots 
    private Compare<T> cmp; //The interface to be used for comparing elements

    public Union(T elements[], Compare<T> cmpInterface)
    {
        /*Initializes the data structure*/

        rootRanks = new HashMap<T,Integer>();
        arrayPositions = new HashMap<T,Integer>();
        cmp = cmpInterface;

        //Initializing the parents list
        this.parent = (T[])new Object[elements.length];
        for(int a = 0; a < elements.length;++a)
        {
            parent[a] = elements[a]; //Initializing each element as its own parent
            arrayPositions.put(elements[a], a); //Mapping the position of the elements parent
            rootRanks.put(elements[a],0); //Rank is 0 as all elements are leaves
        }
    }

    public int find(T e)
    {
        /*Returns the set that the element belongs to or -1 if element does not exist in the data structure*/

        T currentNode = e; //The node currently checked
        int setId = -1;
        try
        {
            setId = arrayPositions.get(currentNode); //The parent of the element
        }
        catch(NullPointerException ex)
        {
            //A NullPointerException is thrown as the element does not exist in the data structure
            return -1;
        }

        ArrayList<Integer> visitedNodes = new ArrayList<Integer>(); //A list of the nodes along the path
        while(cmp.compare(parent[setId],currentNode) != 0)
        {
            visitedNodes.add(setId); //Adding the node to the path
            currentNode = parent[setId]; //Selecting the parent
            setId = arrayPositions.get(currentNode);
        }

        //Performing path compression
        compressPath(visitedNodes, setId);
        
        return setId;
    }

    private void compressPath(ArrayList<Integer> nodes, int root)
    {
        /*Compresses the given path*/

        for(int node : nodes)
        {
            parent[node] = parent[root];
        }
    }

    public boolean union(T x, T y)
    {
        /*Performs union on the sets of the given objects*/

        int xRoot = -1; //The root of x's set
        int yRoot = -1; //The root of y's set

        //Checking if the elements exist 
        if((xRoot = find(x)) == -1 || (yRoot = find(y)) == -1) return false;

        //Getting the ranks of the roots
        int xRank = rootRanks.get(parent[xRoot]);
        int yRank = rootRanks.get(parent[yRoot]);

        //Smaller rank root points to the larger rank root
        if(xRank < yRank)
        {
            rootRanks.remove(parent[xRoot]); //Removing xRoot as it is no longer a root
            parent[xRoot] = parent[yRoot];
        }
        else if(xRank > yRank)
        {
            rootRanks.remove(parent[yRoot]); //Removing yRoot as it is no longer a root
            parent[yRoot] = parent[xRoot];
        }
        else if(xRank == yRank)
        {    
            //Rank of yRoot increases by 1
            int oldRank = rootRanks.get(parent[yRoot]);
            rootRanks.put(parent[yRoot], oldRank+1); //Updating the rank of the root

            rootRanks.remove(parent[xRoot]); //Removing xRoot as it is no longer a root

            parent[xRoot] = parent[yRoot];
        }

        return true;
    }

}