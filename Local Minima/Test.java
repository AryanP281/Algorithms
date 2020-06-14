public class Test {
    
    public static void main(String args[])
    {
        int arr[][] = {{1,5,10 ,100,100,100}, {10,20,15,100, 100,100},{100,100,100,100,100, 100}, {100,100,5, 100,100,100},
    {5,5,1,10,100,100}, {100,100,100,100,100, 100}};

        System.out.println(LocalMin.numOfLocalMin(arr));
        System.out.println(LocalMin.ops);
    }

}