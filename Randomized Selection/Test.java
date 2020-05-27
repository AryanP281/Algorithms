public class Test {
    
    public static void main(String args[])
    {
        Integer arr[] = new Integer[20];
        for(int a = 0; a < arr.length; ++a)
        {
            int max = (int)((double)(arr.length * (arr.length - 1)));
            arr[a] = randomInRange(1,max);
        }

        RSelect<Integer> rs = new RSelect<Integer>();
        int statistic = randomInRange(1, arr.length);
        System.out.println("Statistic(" + statistic + ") = "+ rs.select(arr, statistic));

        QuickSort<Integer> qs = new QuickSort<Integer>();
        qs.sort(arr, null);
        System.out.print("\n\nThe array is : ");
        for(Integer e : arr)
        {
            System.out.print(e + ", ");
        }

    }

    private static int randomInRange(int min, int max)
    {
        return (int)(min + (Math.random() * (double)((max-min)+1)));
    }

}