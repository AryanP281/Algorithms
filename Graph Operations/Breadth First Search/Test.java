
import java.util.*;

public class Test {
    
    public static void main(String args[])
    {

        int graph[][] = {{1,3}, {0,2,3}, {1,9}, {0,1,4}, {3,9}, {6,8}, {5,7}, {6}, {5}, {2,4}, {11}, {10}, {12} };

        ArrayList<ArrayList<Integer>> comps = BreadthFirstSearch.getConnectedComponents(graph);
        System.out.println("The connected components are: ");
        for(ArrayList<Integer> comp : comps)
        {
            for(Integer n : comp)
            {
                System.out.print(n + ", ");
            }
            System.out.print('\n');
        }

    }
}