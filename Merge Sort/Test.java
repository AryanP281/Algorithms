

import CustomDataStructures.Compare.*;

public class Test {

    public static void main(String args[])
    {
        Integer arr[] = new Integer[1000];
        System.out.println("Before Sorting:");
        for(short a = 0; a < arr.length; ++a)
        {
            arr[a] = (int)(Math.random() * 100000);
            System.out.print(arr[a] + ", ");
        }

        MergeSort<Integer> sorter = new MergeSort<Integer>(new DefaultCompare<Integer>());
        sorter.mergeSort(arr, 0, arr.length-1);

        System.out.println("\nAfter Merge sorting: ");
        for(Integer i : arr)
        {
            System.out.print(i + ", ");
        }
        System.out.println("\nSorted ? " + sorted(arr));
    }

    private static boolean sorted(Integer arr[])
    {
        for(int a = 1; a < arr.length; ++a)
        {
            if(arr[a] - arr[a-1] < 0) return false;
        }

        return true;
    }
    
}