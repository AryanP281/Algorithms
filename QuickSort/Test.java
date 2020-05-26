public class Test {
    
    public static void main(String args[])
     {
        Integer arr[] = new Integer[100];
        System.out.print("The array is : ");
        for(int a = 0; a < arr.length; ++a)
        {
            arr[a] = (int)(Math.random() * 100.0);
            System.out.print(arr[a] + ", ");
        }

        QuickSort<Integer> sort = new QuickSort<Integer>();
        sort.sort(arr, sort.randomPivot);

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
}