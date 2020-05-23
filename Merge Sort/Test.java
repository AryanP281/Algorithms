public class Test {

    public static void main(String args[])
    {
        Integer arr[] = new Integer[1000];
        System.out.println("Before Sorting:");
        for(short a = 0; a < arr.length; ++a)
        {
            arr[a] = (int)(Math.random() * 1000);
            System.out.print(arr[a] + ", ");
        }

        MergeSort<Integer> sorter = new MergeSort<Integer>();
        sorter.mergeSort(arr, 0, arr.length-1);

        System.out.println("\nAfter Merge sorting: ");
        for(Integer i : arr)
        {
            System.out.print(i + ", ");
        }

    }
    
}