public class Test {
    
    public static void main(String[] args)
    {
        int arr[] = new int[10000];
        int modalLoc = random(0, arr.length-1);
        for(int a = 0; a < arr.length;++a)
        {
            if(a<=modalLoc) arr[a] = a+1;
            else arr[a] = arr[modalLoc] - (5*(a-modalLoc));
            System.out.print(arr[a] + ", ");
        }
        System.out.println('\n');

        int exp = arr[modalLoc];
        int y = Max.maxInUnimodal(arr);
        System.out.println("Expected: " + exp);
        System.out.println("Returned: "+ y);
        System.out.println(Max.ops);

    }

    private static int random(int min, int max)
    {
        return min + (int)(Math.random() * ((max-min)+1));
    }

}