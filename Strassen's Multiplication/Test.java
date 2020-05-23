public class Test {
    
    public static void main(String args[])
    {
        Matrix x = new Matrix(new int[][] { {1,1,1,1}, {1,1,1,1}, {1,1,1,1}, {1,1,1,1} } );
        Matrix y = new Matrix(new int[][] { {1,1,1,1}, {1,1,1,1}, {1,1,1,1}, {1,1,1,1} } );

        Matrix prod = x.multiply(y);
        prod.print();
    }
}