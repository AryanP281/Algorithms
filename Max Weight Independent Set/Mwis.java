
import java.util.*;

class MaxWeightIndependentSet
{
    
    MaxWeightIndependentSet() {}

    Pair<ArrayList<Integer>, Integer> getSet(int vertices[])
    {
        /*Finds and returns the max weight independent set
        vertices = the vertices and their weights*/

        ArrayList<Integer> subsets[] = new ArrayList[vertices.length]; //The max weight subsets
        int maxWeights[] = new int[vertices.length]; //The max weights for the subsets

        //Handling the 1st vertex
        subsets[0] = new ArrayList<Integer>();
        subsets[0].add(0);
        maxWeights[0] = vertices[0];

        //Handling the 2nd vertex
        subsets[1] = new ArrayList<Integer>();
        subsets[1].add(vertices[1] > vertices[0] ? 1 : 0);
        maxWeights[1] = Math.max(vertices[0], vertices[1]);

        //Determining the max weight independent subsets
        for(int a = 2; a < vertices.length; ++a)
        {
            subsets[a] = new ArrayList<Integer>();
            
            if (maxWeights[a-1] > maxWeights[a-2] + vertices[a])
            {
                subsets[a] = (ArrayList<Integer>)subsets[a-1].clone();
                maxWeights[a] = maxWeights[a-1];
            }
            else
            {
                subsets[a] = (ArrayList<Integer>)subsets[a-2].clone();
                subsets[a].add(a);
                
                maxWeights[a] = maxWeights[a-2] + vertices[a];
            }
        }

        return new Pair<ArrayList<Integer>,Integer>(subsets[subsets.length - 1], maxWeights[maxWeights.length - 1]);
    }
}

class Pair<V1,V2>
{
    V1 element1; //The 1st element of the pair
    V2 element2; //The 2nd element of the pair

    Pair(V1 e1, V2 e2)
    {
        element1 = e1;
        element2 = e2;
    }
}