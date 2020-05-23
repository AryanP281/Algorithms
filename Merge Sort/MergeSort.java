import java.util.Arrays;


class MergeSort<T extends Comparable<T>>
{

    MergeSort() {}

    private void merge(T arr[], int l, int r, int m)
    {
        //Isolating the two sections of the array
        T p1[] = Arrays.copyOfRange(arr, l, l+m+1);
        T p2[] = Arrays.copyOfRange(arr, l+m+1, r+1);

        int i = 0;
        int j = 0;
        for(int k = l; k <= r; ++k)
        {
            //Checking if one of the sub arrays has been completely traversed
            if(i >= p1.length)
           {
               arr[k] = p2[j];
               ++j;
               continue;
           }
           if(j >= p2.length)
           {
               arr[k] = p1[i];
               ++i;
               continue;
           }

           if(p1[i].compareTo(p2[j]) < 0)
           {
                arr[k] = p1[i];
                ++i;
           }
           else
           {
                arr[k] = p2[j];
                ++j; 
           }

        }

    }

    void mergeSort(T arr[], int l, int r)
    {
        if(l < r)
        {
            int midpoint = (r-l) / 2; //Getting the midpoint of the section of the array
            mergeSort(arr, l, l+midpoint); //Sorting the 1st half of the array
            mergeSort(arr, l+midpoint+1, r); //Sorting the 2nd half of the array

            merge(arr, l, r, midpoint); //Merging the two sorted arrays
        }
    }

}