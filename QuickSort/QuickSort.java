import java.util.*;

import CustomDataStructures.Compare.Compare;

//An interface for pivot selection
interface SelectPivot<T>
{ 
    int selectPivot(T arr[], int l, int r); //Returns the index of the pivot element
}

class QuickSort<T>
{
    
    long numOfComparisons = 0;
    
    //Creating some anonymous classes for pivot selection
    SelectPivot<T> randomPivot = new SelectPivot<T>(){

        @Override
        public int selectPivot(T arr[], int l, int r)
        {
           return getRandomInRange(l, r);
        }

        private int getRandomInRange(int min, int max)
        {
            return min + (int)(Math.random() * (double)((max-min)+1));
        }

    }; //Returns a random pivot from the array

    SelectPivot<T> firstElementPivot = new SelectPivot<T>() {
        @Override
        public int selectPivot(T arr[], int l, int r)
        {
            return l;
        }
    }; //Returns the 1st element of the array as the pivot

    SelectPivot<T> lastElementPivot = new SelectPivot<T>(){
        @Override
        public int selectPivot(T arr[], int l, int r)
        {
            return r;
        }
    }; //Returns the last element of the array as the pivot

    private Compare<T> cmp; //The interface to be used for comparing elements
    
    QuickSort(Compare<T> comparisonInterface) 
    {
        this.cmp = comparisonInterface;
    }

    void sort(T arr[], SelectPivot<T> pivotSelection)
    {
        quickSort(arr,0, arr.length-1, pivotSelection);
    }

    private void quickSort(T arr[], int l, int r, SelectPivot<T> pivotSelection)
    {   
        //The base case
        if(l>=r || l < 0 || r > arr.length) return ;

        numOfComparisons += r-l;

        //Getting the pivot
        int pivotIndex = pivotSelection == null ? randomPivot.selectPivot(arr, l, r) : pivotSelection.selectPivot(arr, l, r);
        swap(arr, l, pivotIndex); //Shifting the pivot element to the front of the array

        //Partitioning the array around the pivot
        pivotIndex = partition(arr, l, r);

        //Sorting the 1st subarray i.e elements < pivot
        quickSort(arr, l, pivotIndex-1, pivotSelection);
        //Sorting the 2nd subarray i.e elements > pivot
        quickSort(arr, pivotIndex+1, r, pivotSelection);
    }

    private void swap(T arr[], int index1, int index2)
    {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    private int partition(T arr[], int l, int r)
    {
        //Partioning the array
        int i = l;
        for(int j = l+1; j <= r; ++j)
        {
            if(cmp.compare(arr[j], arr[l]) < 0) swap(arr, ++i, j);
        }

        //Placing the pivot element in its right place
        swap(arr, l, i);

        //Returning the new index of the pivot
        return i;
    }

}