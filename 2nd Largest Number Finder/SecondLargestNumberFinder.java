// a = 2, b = 2, d = 0
// a < (b^d)
//T(n) = O(n^(loga / log b)) = O(n)


class SecondLargestNumberFinder
{
    static int ops = 0;
    static int getSecLargest(int arr[])
	{
        int largest = getLargest(arr, 0, arr.length - 1);

        int secLargest = arr[0];
        for(int a = 0; a < arr.length; ++a)
        {
            if(arr[a] == largest) continue;

            if(arr[a] > secLargest) secLargest = arr[a];
        }

        return secLargest;
    }
    
    private static int getLargest(int arr[], int l, int r)
    {
        ++ops;
        if(r-l+1 <= 2)
            return Math.max(arr[l], arr[r]);
    
        int m = (r-l)/2;
        int max1 = getLargest(arr,l,l+m);
        int max2 = getLargest(arr,l+m+1, r);
        
        return Math.max(max1, max2);
    }
}