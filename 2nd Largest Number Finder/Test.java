import java.util.*;

public class Test {
    
    public static void main(String args[])
    {
        int arr[] = new int[150];
        System.out.print("The array is: ");
        for(int a = 0; a < arr.length; ++a)
        {
            arr[a] = (int)(Math.random() * 100);
            System.out.print(arr[a] + ", ");
        }
        System.out.println("\n");

        System.out.println("The 2nd largest number is : " + SecondLargestNumberFinder.getSecLargest(arr));
    }
}