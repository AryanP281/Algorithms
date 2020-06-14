

class InsertionSort<T extends Comparable<T>>
{
    InsertionSort() {}

    void sortAscending(T arr[])
    {
        for(int a = 1; a < arr.length;++a)
        {
            T key = arr[a];
            int b = a-1;
            while(b >= 0 && key.compareTo(arr[b]) < 0)
            {
                arr[b+1] = arr[b];
                b--;
            }

            arr[b+1] = key;
        }
    }

    void sortDescending(T arr[])
    {
        for(int a = 1; a < arr.length;++a)
        {
            T key = arr[a];
            int b = a-1;
            while(b >= 0 && key.compareTo(arr[b]) > 0)
            {
                arr[b+1] = arr[b];
                b--;
            }

            arr[b+1] = key;
        }
    }
}