import java.util.*;
import java.util.stream.*;;

class Point2D
{
    int x;
    int y;

    Point2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    double distanceFrom(Point2D p)
    {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }
}

class ClosestPair
{

    static Point2D[] getClosestPair(Point2D arr[])
    {
        //Sorting the points by x-coords and y-coords
        Point2D px[] = arr.clone();
        MergeSortPoints.mergeSort(px, 0, px.length - 1, true);
        Point2D py[] = arr.clone();
        MergeSortPoints.mergeSort(py, 0, py.length - 1, false);

        return closestPair(px, py, 0, arr.length-1);
    }

    private static Point2D[] closestPair(Point2D px[], Point2D py[], int l, int r)
    {
        //The base case
        if(r-l+1 <= 4)
        {
            double min = -1;
            Point2D closestPoints[] = new Point2D[2];
            for(byte a = (byte)l; a < r;++a)
            {
                for(byte b = (byte)(a+1); b <= r; ++b)
                {
                    double distance = px[a].distanceFrom(px[b]);
                    if(distance < min || min == -1)
                    {
                        min = distance;
                        closestPoints[0] = px[a];
                        closestPoints[1] = px[b];
                    }
                }
            }
            return closestPoints;
        }

        int midpoint = (r-l) / 2;
        Point2D p1[] = closestPair(px, py, l, l+midpoint); //Getting the closest points in the 1st half of the array Px
        Point2D p2[] = closestPair(px, py, l+midpoint+1, r); //Getting the closest points in the 2nd half of the array Px

        //Calculating the min distance between points from either side
        double delta = getDelta(p1, p2);
        Point2D p3[] = closestSplitPair(px, py, l, r, midpoint, delta);

        return p3[0] == null ? closest(new Point2D[][]{p1, p2}) : closest(new Point2D[][]{p1, p2, p3});
    }

    private static double getDelta(Point2D p1[], Point2D p2[])
    {
        double d1 = p1[0].distanceFrom(p1[1]);
        double d2 = p2[0].distanceFrom(p2[1]);

        return d1 < d2 ? d1 : d2;
    }

    private static Point2D[] closestSplitPair(Point2D px[], Point2D py[], int l, int r, int m, double delta)
    {
        int xBar = px[l+m].x; //Getting the max x-coordinate in the 1st half of the array

        //Getting the points with x-coordinates between [xBar - delta, xBar + delta] sorted according to y-coordinates
        ArrayList<Point2D> sy = new ArrayList<Point2D>();
        for(int a = l; a < r+1; ++a)
        {
            if(py[a].x >= (xBar - delta) && py[a].x <= (xBar + delta))
                sy.add(py[a]);
        }

        double min = -1;
        Point2D closestPoints[] = {null, null};
        for(int a = 0; a < sy.size() - 1; ++a)
        {
            for(int b = a+1; b < Math.min(a+7, sy.size() - 1); ++b)
            {
                double distance = (sy.get(a)).distanceFrom(sy.get(b));
                if(distance < min || min == -1)
                {
                    min = distance;
                    closestPoints[0] = sy.get(a);
                    closestPoints[1] = sy.get(b);
                }
            }
        }

        return closestPoints;
    }

    private static Point2D[] closest(Point2D points[][])
    {
        double min = points[0][0].distanceFrom(points[0][1]);
        Point2D closestPoints[] = points[0];
        for(int a = 1; a < points.length; ++a)
        {
            double distance = points[a][0].distanceFrom(points[a][1]);
            if(distance < min)
            {
                min = distance;
                closestPoints = points[a];
            }
        }

        return closestPoints;
    }

}