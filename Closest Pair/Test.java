public class Test {

    public static void main(String args[])
    {
        Point2D pts[] = {new Point2D(0,0), new Point2D(-4,1), new Point2D(-7,-2), new Point2D(4,5), new Point2D(1,1)};

        Point2D closest[] = ClosestPair.getClosestPair(pts);
    }
    
}