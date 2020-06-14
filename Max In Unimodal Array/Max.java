
class Max
{
    static int ops = -1;
    static int maxInUnimodal(int arr[])
    {
        return getMax(arr, 0, arr.length-1);
    }

    private static int getMax(int arr[], int l, int r)
    {
        ops += ops == -1 ? 1 : 5;
        if(r==l) return arr[l];

        int midpoint = (r-l) / 2;
        if(arr[l+midpoint] - arr[l+midpoint-1] > 0 && arr[l+midpoint+1] - arr[l+midpoint] > 0) return getMax(arr, l+midpoint+1, r);
        else if(arr[l+midpoint] - arr[l+midpoint - 1] < 0 && arr[l+midpoint+1] - arr[l+midpoint] < 0) return getMax(arr, l, l+midpoint);

        return arr[l+midpoint];
    }
}