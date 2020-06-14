
import java.util.*;

class MaxSumSubarray
{
    MaxSumSubarray(){}

    static int[] getMaxSumSubarray(int[] arr)
    {
        int res[] = getSubarray(arr, 0, arr.length-1);

        return new int[]{res[1], res[2]};
    }

    private static int[] getSubarray(int arr[], int l, int r)
    {
        //Base case
        if(l == r)
            return new int[]{arr[l], l,r};

        int midpoint = (r-l)/2; //Calculating the midpoint of the subarray
        int leftRes[] = getSubarray(arr, l, l+midpoint); //Getting the max sum subarray in the left half
        int rightRes[] = getSubarray(arr, l+midpoint+1, r); //Getting the max sum subarray in the right half
        int midRes[] = getMidSum(arr, l,midpoint,r); //Getting the max sum subarray in middle portion

        return (leftRes[0] > midRes[0]) ? ((leftRes[0] > rightRes[0]) ? leftRes : rightRes) : ((midRes[0] > rightRes[0]) ? midRes : rightRes); 

    }

    private static int[] getMidSum(int arr[], int l, int m, int r)
    {
        int maxSumLeft = arr[l+m];
        int i = l+m;
        int sum = maxSumLeft;
        for(int a = l+m-1; a >= l;--a)
        {
            sum += arr[a];
            if(sum > maxSumLeft)
            {
                maxSumLeft = sum;
                i = a;
            }
        }

        int maxSumRight = arr[l+m+1];
        sum = maxSumRight;
        int j = l+m+1;
        for(int b = l+m+2; b <= r; ++b)
        {
            sum += arr[b];
            if(sum > maxSumRight)
            {
                maxSumRight = sum;
                j = b;
            }
        }

        return new int[]{maxSumLeft+maxSumRight, i, j};
    }
}