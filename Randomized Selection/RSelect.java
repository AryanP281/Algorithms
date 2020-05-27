
import java.util.*;

class RSelect<T extends Comparable<T>>
{
    RSelect(){}

    T select(T arr[], int statistic)
    {
        return rSelect(arr,0,arr.length-1,statistic);
    }

    private T rSelect(T arr[], int l, int r, int statistic)
    {
        //The base case
        if(l >= r) return arr[l];

        //Selecting a random pivot
        int pivotIndex = randomInRange(l, r);
        swap(arr,l,pivotIndex); //Moving the pivot to the front of the array

        //Partitioning and getting the new pivot index
        pivotIndex = partition(arr, l, r);
        
        //Checking if the desired statistic lies in the right half 
        if(statistic > (pivotIndex+1)) return rSelect(arr, pivotIndex+1, r, statistic);
        //Checking if the desired statistic lies in the right half 
        else if(statistic < (pivotIndex+1)) return rSelect(arr, l, pivotIndex-1, statistic);

        return arr[pivotIndex]; //Checking if the statistic is the pivot 
    }

    private int partition(T arr[], int l, int r)
    {
        //Partitioning the array
        int i = l;
        for(int j = l+1; j <= r; ++j)
        {
            if(arr[j].compareTo(arr[l]) < 0) swap(arr, ++i, j);
        }

        //Placing the pivot element
        swap(arr,l,i);

        return i;
    }

    private int randomInRange(int min, int max)
    {
        return (int)(min + (Math.random() * (double)((max-min)+1)));
    }

    private void swap(T arr[], int index1, int index2)
    {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

}