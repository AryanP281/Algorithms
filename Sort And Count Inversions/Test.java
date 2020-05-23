public class Test {
    
    public static void main(String args[])
    {
        Integer arr[] = {1,2,4,6,5};
        SortAndCount<Integer> inversionCounter = new SortAndCount<Integer>();

        System.out.println("Number of inversions = " + inversionCounter.sortAndCountInversions(arr, 0, arr.length - 1));
    }

}