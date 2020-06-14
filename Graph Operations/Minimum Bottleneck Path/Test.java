public class Test {
    
    public static void main(String args[])
    {
        //int graph[][][] = { {{1,6}, {3,7}}, {{2,5}, {3,2}, {4,2}}, {{4,5}}, {{4,1}}, {{4,0}} };
        int graph[][][] = { {{1,6}, {3,7}}, {{0,6}, {2,5}, {3,2}, {4,2}}, {{1,5}, {4,5}}, {{0,7}, {1,2}, {4,1}}, {{1,2}, {2,5}, {3,1}} };

        int minBottleneck[][] = MinBottleneckPath.getMinBottleneckPath_Linear(graph, 0, 4);
        System.out.print("Path: ");
        for(int a = 0;a < minBottleneck[1].length;++a)
        {
            System.out.print(minBottleneck[1][a] + "->");
        }
        System.out.println(String.format("\nMin-bottleneck weight = %d", minBottleneck[0][0]));

    }

}