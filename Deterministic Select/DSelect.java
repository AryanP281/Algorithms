
import java.util.*;

class DSelect
{
    DSelect(){}

    int select(int arr[], int statistic)
    {
        return dSelect(arr,0,arr.length-1,statistic);
    }

    private int dSelect(int arr[], int l, int r, int statistic)
    {
        //The base case
        if(l==r) return arr[l];

        //Getting the pivot index
        int pivotIndex = indexOf(arr,getPivot(arr,l,r));
        swap(arr, l, pivotIndex); //Moving the pivot to the front of the array

        //Partitioning and getting the new pivot index
        pivotIndex = partition(arr, l, r);
        
        //Checking if the desired statistic lies in the right half 
        if(statistic > (pivotIndex+1)) return dSelect(arr, pivotIndex+1, r, statistic);
        //Checking if the desired statistic lies in the right half 
        else if(statistic < (pivotIndex+1)) return dSelect(arr, l, pivotIndex-1, statistic);

        return arr[pivotIndex]; //Checking if the statistic is the pivot 

    }

    private int getPivot(int arr[], int l, int r)
    {
        /*Uses Median Of Medians to determine the ideal pivot*/

        if(r-l+1 < 5)
        {
            int[] subArray = Arrays.copyOfRange(arr, l, r+1);
            bubbleSort(subArray);
            return subArray[subArray.length / 2];
        }

        int c[] = new int[(r-l+1) / 5]; //The medians of the n/5 groups
        for(int g = 0; g < (r-l+1) / 5;g++)
        {
            int a[] = Arrays.copyOfRange(arr, l+(5*g), l+(5*g)+5);
            //Sorting the 5 element array
            bubbleSort(a);
            //Adding the median of the 5-element array to the list of medians
            c[g] = a[2];
        }
        
        //Finding the median of medians
        return dSelect(c, 0, c.length-1, c.length / 2);
    }

    private void bubbleSort(int arr[])
    {
        for(int a = 0; a < arr.length-1; ++a)
        {
            for(int b = 0; b < arr.length - 1 - a; ++b)
            {
                if(arr[b] > arr[b+1]) swap(arr,b,b+1); 
            }
        }
    }

    private void swap(int arr[], int index1, int index2)
    {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    private int indexOf(int arr[], int e)
    {
        for(int a = 0; a < arr.length; ++a)
        {
            if(arr[a] == e ) return a;
        }
        return -1;
    }

    private int partition(int arr[], int l, int r)
    {
        //Partitioning the array
        int i = l;
        for(int j = l+1; j <= r; ++j)
        {
            if(arr[j] < arr[l]) swap(arr, ++i, j);
        }

        //Placing the pivot element
        swap(arr,l,i);

        return i;
    }

}