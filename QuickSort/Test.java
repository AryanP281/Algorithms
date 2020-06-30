
import java.util.*;
import java.io.*;
import CustomDataStructures.Compare.*;

public class Test {
    
    public static void main(String args[]) 
     {
        Integer arr[] = new Integer[1000];
        System.out.print("The array is : ");
        for(int a = 0; a < arr.length; ++a)
        {
            arr[a] = (int)(Math.random() * 10000.0);
            System.out.print(arr[a] + ", ");
        }

        /*Integer arr[] = readFromFile("Assignment.txt");
        SelectPivot<Integer> medianOfThree = new SelectPivot<Integer>()
        {
            @Override
            public int selectPivot(Integer arr[], int l, int r)
            {
                int first = arr[l];
                int last = arr[r];
                int mid = arr[(r-l) / 2];

                return first>last ? (last>mid ? r : ((r-l)/2)) : (first>mid?l:((r-l)/2));
            }
        };*/

        QuickSort<Integer> sort = new QuickSort<Integer>(new DefaultCompare<Integer>());
        sort.sort(arr, sort.randomPivot);
        System.out.println("Number of comparisons = " + sort.numOfComparisons);

        System.out.println("\nAfter sorting: ");
        for(Integer e : arr)
        {
            System.out.print(e + ", ");
        }

        System.out.println("\nSorted: " + check(arr));

    }

    private static boolean check(Integer arr[])
    {
        for(int a = 1; a < arr.length; ++a)
        {
            if(arr[a] - arr[a-1]  < 0) return false; 
        }

        return true;
    }

    private static Integer[] readFromFile(String path) throws IOException
    {
        ArrayList<Integer> data = new ArrayList<Integer>();

        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = "";
        while((line = reader.readLine()) != null)
        {
            data.add(Integer.parseInt(line));
        }
        reader.close();

        return data.toArray(new Integer[data.size()]);
    }

}