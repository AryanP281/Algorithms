
class IndexIsElement
{

    static boolean indexIsElement(int arr[])
    {
        return check(arr, 0, arr.length-1);
    }

    private static boolean check(int arr[], int l, int r)
    {
        if(l==r) return arr[l] == l;

        int midpoint = (r-l)/2;
        if(arr[l+midpoint] < l+midpoint) return check(arr, l+midpoint+1, r);
        else if(arr[l+midpoint] > l+midpoint) return check(arr, l, l+midpoint);

        return true;
    }
}