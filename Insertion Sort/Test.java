
import java.util.*;

public class Test {
    
    public static void main(String args[])
    {
        Integer arr[] = new Integer[1000];
        System.out.println("Before sorting...");       
        for(int a = 0; a < arr.length;++a)
        {
            arr[a] = (int)(Math.random() * 100000.0);
            System.out.print(arr[a] + ", ");
        }

        InsertionSort<Integer> s = new InsertionSort<Integer>();
        s.sortDescending(arr);
        if(checkSorting(arr, false))
        {
            System.out.println("\nAfter sorting....");
            for(Integer e : arr)
            {
                System.out.print(e + ", ");
            }
        }
        else System.out.println("Incorrect Sorting");

    }

    static boolean checkSorting(Integer arr[], boolean asc)
    {
        for(int a = 1; a < arr.length;++a)
        {
            if(asc)
            {
                if(arr[a] < arr[a-1]) return false;
            }
            else
                if(arr[a] > arr[a-1]) return false;
        }

        return true;
    }

}