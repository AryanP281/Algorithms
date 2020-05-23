import java.util.Arrays;

class SortAndCount<T extends Comparable<T>>
{
    SortAndCount(){}

    int sortAndCountInversions(T arr[], int l, int r)
    {
        if(l == r) return 0;

        int midpoint = (int)((r-l)/2);
        int leftInversions = sortAndCountInversions(arr, l, l+midpoint); //The inversions in the left sub-array
        int rightInversions = sortAndCountInversions(arr, l+midpoint+1, r); //The inversions in the right sub-array
        int splitInversions = mergeAndCountSplitInversions(arr, l, r, midpoint); //The split inversions

        return leftInversions + rightInversions + splitInversions; //Returning the total number of inversions
        
    }

    private int mergeAndCountSplitInversions(T arr[], int l, int r, int m)
    {
        //Copying the sub-arrays
        T p1[] = Arrays.copyOfRange(arr, l, l+m+1);
        T p2[] = Arrays.copyOfRange(arr, l+m+1, r+1);

        //Sorting and counting the split inversions
        int i,j,splitInversions;
        i = j = splitInversions = 0;
        for(int k = l; k <= r; ++k)
        {
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

            if(p1[i].compareTo(p2[j]) <= 0)
            {
                arr[k] = p1[i];
                ++i;
                continue;
            }
            else
            {
                arr[k] = p2[j];
                ++j;
                splitInversions += p1.length - i; /*Incrementing the no. of inversions by the remaining elements in left-subarray as 
                they occur before p2[j]. As p1 is sorted and p1[i] > p2[j], all the elements from j to (p1.length - 1) will be greater
                than p2[j].*/
                continue;
            }

        }

        return splitInversions;
    }

}