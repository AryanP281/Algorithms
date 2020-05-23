import java.util.Arrays;


class MergeSortPoints
{

    static private void merge(Point2D arr[], int l, int r, int m, boolean sortByX)
    {
        //Isolating the two sections of the array
        Point2D p1[] = Arrays.copyOfRange(arr, l, l+m+1);
        Point2D p2[] = Arrays.copyOfRange(arr, l+m+1, r+1);

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

           if((sortByX && p1[i].x < p2[j].x) || (!sortByX && p1[i].y < p2[j].y))
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

    static void mergeSort(Point2D arr[], int l, int r, boolean sortByX)
    {
        if(l < r)
        {
            int midpoint = (r-l) / 2; //Getting the midpoint of the section of the array
            mergeSort(arr, l, l+midpoint, sortByX); //Sorting the 1st half of the array
            mergeSort(arr, l+midpoint+1, r, sortByX); //Sorting the 2nd half of the array

            merge(arr, l, r, midpoint, sortByX); //Merging the two sorted arrays
        }
    }

}