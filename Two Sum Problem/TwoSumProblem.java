

import CustomDataStructures.HashTable.HashTable;
import CustomDataStructures.Compare.*;

class TwoSumProblem
{
    public static void main(String args[])
    {
        HashTable<Integer, Integer> ht = new HashTable<Integer, Integer>(0.75, new DefaultCompare<Integer>());
        Integer arr[] = new Integer[100000];
        for(int a = 0; a < arr.length;++a)
        {
            arr[a] = (int)(Math.random() * 10000000.0);
            ht.put(arr[a], arr[a]);
        }

        int x = arr[randomInRange(0, arr.length)];
        int y = arr[randomInRange(0, arr.length)];
        int sum = x + y;
        for(Integer e : arr)
        {
            Integer f = ht.get(sum - e);
            if(f != null) System.out.println(e + "+" + (sum-e) + "=" + sum);
        }

    }

    private static int randomInRange(int min, int max)
    {
        return (int)(min + (Math.random() * (max - min + 1)));
    }
}