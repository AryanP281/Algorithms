
class LocalMin
{
    static int ops = -1;
    static int numOfLocalMin(int arr[][])
    {
        return localMins(arr, new int[]{0,0}, new int[]{arr.length-1, arr.length-1});
    }

    private static int localMins(int arr[][], int l[], int r[])
    {
        ops++;
        int n = (r[0] - l[0])*arr.length + (r[1] - l[1]) + 1;
        if(n <= 4)
        {
            int localMins = 0;
            int start = l[0]*arr.length + l[1];
            for(int i = 0; i < n; ++i)
            {
                int a = (start+i) / arr.length;
                int b = (start+i) % arr.length;

                //Checking if lower than top tile
                try
                {
                    if(arr[a][b] >= arr[a-1][b]) continue;
                }
                catch(IndexOutOfBoundsException e) {}

                //Checking the right tile
                try
                {
                    if(arr[a][b] >= arr[a][b+1]) continue;
                }
                catch(IndexOutOfBoundsException e) {}

                //Checking the bottom tile
                try
                {
                    if(arr[a][b] >= arr[a+1][b]) continue;
                }
                catch(IndexOutOfBoundsException e) {}

                //Checking the right tile
                try
                {
                    if(arr[a][b] >= arr[a][b-1]) continue;
                }
                catch(IndexOutOfBoundsException e) {}

                ++localMins;
            }
            return localMins;
        }
        
        n = ((l[0]* arr.length + l[1]) + (n/2))-1;
        int midpoint[] = {(n / (arr.length)), (((n) % arr.length))};
        
        //Getting the local min in 1st half of grid
        int localMin = localMins(arr, l, midpoint);
        //Getting the local min in 2nd half of grid
        int l2[] = new int[2];
        if(midpoint[1] == arr.length-1)
        {
            l2[0] = midpoint[0] + 1;
            l2[1] = 0;
        }
        else
        {
            l2[0] = midpoint[0];
            l2[1] = midpoint[1] + 1;
        }

        localMin += localMins(arr, l2, r);
        
        return localMin;
    }

}