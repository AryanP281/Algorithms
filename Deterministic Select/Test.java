
import java.util.*;

public class Test {
    
    public static void main(String args[])
    {
        int arr[] = new int[20];
        Integer sortedArr[] = new Integer[arr.length];
        int max = arr.length * (arr.length-1);
        for(int a = 0; a < arr.length; ++a)
        {
            arr[a] = randomInRange(1, max);
            sortedArr[a] = arr[a];
        }

        QuickSort<Integer> qs = new QuickSort<Integer>();
        qs.sort(sortedArr, null);
        System.out.print("The array is : ");
        for(Integer e : sortedArr)
        {
            System.out.print(e + ", ");
        }

        DSelect ds = new DSelect();
        int statistic = randomInRange(1, arr.length);
        int res = ds.select(arr, statistic);
        System.out.println("\nStatistic(" + statistic + ") = "+ res);
        System.out.println("Array[" + statistic + "] = " + sortedArr[statistic-1]);
    }

    private static int randomInRange(int min, int max)
    {
        return (int)(min + (Math.random() * (double)(max-min+1)));
    }
}