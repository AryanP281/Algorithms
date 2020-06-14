public class Test {
    
    public static void main(String args[])
    {
        int graph[][][] = {{ {1,6}, {3,1} }, { {2,5}, {3,2}, {4,2} }, { {4,5} }, { {4,1} }, {{4,0}} };

        int path[][] = DijkstraShortestPath.getShortestPath(graph, 0, 4);
        System.out.println("Path:");
        for(int node : path[1])
        {
            System.out.print(node + "->");
        }
        System.out.println("\nPath Length = " + path[0][0]);

    }
}