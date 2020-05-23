
class Matrix
{
    int data[][];

    Matrix(int size)
    {
        data = new int[size][size];
    }

    Matrix(int vals[][])
    {
        data = vals;
    }

    void set(int row, int col, int val)
    {
        data[row][col] = val;
    }

    int get(int row, int col)
    {
        return data[row][col];
    }

    Matrix getSubMatrix(int rowLimits[], int columnLimits[])
    {
        Matrix subMatrix = new Matrix(rowLimits[1] - rowLimits[0]);

        for(int r = rowLimits[0]; r < rowLimits[1]; ++r)
        {
            for(int c = columnLimits[0]; c < columnLimits[1]; ++c)
            {
                subMatrix.data[r - rowLimits[0]][c - columnLimits[0]] = this.data[r][c];
            }
        }

        return subMatrix;
    }

    int size()
    {
        return data.length;
    }

    void print()
    {
        System.out.print("\n[ ");
        for(int r = 0; r < data.length;++r)
        {
            for(int c = 0; c < data.length; ++c)
            {
                System.out.print(data[r][c] + ", ");
            }
            System.out.print("\n");
        }
        System.out.print("]\n");
    }

    Matrix add(Matrix y)
    {
        Matrix sum = new Matrix(this.size());

        for(int r = 0; r < sum.size(); ++r)
        {
            for(int c = 0; c < sum.size(); ++c)
            {
                sum.set(r,c, this.get(r,c) + y.get(r,c));
            }
        }

        return sum;
    }

    Matrix subtract(Matrix y)
    {
        Matrix diff = new Matrix(this.size());

        for(int r = 0; r < diff.size(); ++r)
        {
            for(int c = 0; c < diff.size(); ++c)
            {
                diff.set(r,c, this.get(r,c) - y.get(r,c));
            }
        }

        return diff;
    }

    Matrix multiply(Matrix y)
    {
        return strassenMultipication(this, y);
    }

    private static Matrix strassenMultipication(Matrix x, Matrix y)
    {
        if(x.size() == 1 && y.size() == 1)
        {
            Matrix prod = new Matrix(1);
            prod.set(0,0, x.get(0,0) * y.get(0,0));
            return prod;
        }

        //Getting the sub matrices
        Matrix a = x.getSubMatrix(new int[]{0, x.size() / 2} , new int[]{0, x.size() / 2});
        Matrix b = x.getSubMatrix(new int[]{0, x.size() / 2} , new int[]{x.size() / 2, x.size()});
        Matrix c = x.getSubMatrix(new int[]{x.size() / 2, x.size()} , new int[]{0, x.size() / 2});
        Matrix d = x.getSubMatrix(new int[]{x.size() / 2, x.size()} , new int[]{x.size() / 2, x.size()});
        Matrix e = y.getSubMatrix(new int[]{0, y.size() / 2} , new int[]{0, y.size() / 2});
        Matrix f = y.getSubMatrix(new int[]{0, y.size() / 2} , new int[]{y.size() / 2, y.size()});
        Matrix g = y.getSubMatrix(new int[]{y.size() / 2, y.size()} , new int[]{0, y.size() / 2});
        Matrix h = y.getSubMatrix(new int[]{y.size() / 2, y.size()} , new int[]{y.size() / 2, y.size()});

        //Calculating the seven products
        Matrix p1 = strassenMultipication(a, f.subtract(h));
        Matrix p2 = strassenMultipication(a.add(b), h);
        Matrix p3 = strassenMultipication(c.add(d), e);
        Matrix p4 = strassenMultipication(d, g.subtract(e));
        Matrix p5 = strassenMultipication(a.add(d), e.add(h));
        Matrix p6 = strassenMultipication(b.subtract(d), g.add(h));
        Matrix p7 = strassenMultipication(a.subtract(c), e.add(f));

        //Calculating the product
        Matrix prod = new Matrix(x.size());
        Matrix h1 = ((p5.add(p4)).subtract(p2)).add(p6);
        for(int row = 0; row < x.size() / 2; ++row)
        {
            for(int col = 0; col < x.size() / 2; ++col)
            {
                prod.data[row][col] = h1.data[row][col];
            }
        }
        Matrix h2 = p1.add(p2);
        for(int row = 0; row < x.size() / 2; ++row)
        {
            for(int col = x.size() / 2; col < x.size(); ++col)
            {
                prod.data[row][col] = h2.data[row][col - (x.size() / 2)];
            }
        }
        Matrix h3 = p3.add(p4);
        for(int row = x.size() / 2; row < x.size(); ++row)
        {
            for(int col = 0; col < x.size() / 2; ++col)
            {
                prod.data[row][col] = h2.data[row - (x.size() / 2)][col];
            }
        }
        Matrix h4 = ((p1.add(p5)).subtract(p3)).subtract(p7);
        for(int row = x.size() / 2; row < x.size(); ++row)
        {
            for(int col = x.size() / 2; col < x.size(); ++col)
            {
                prod.data[row][col] = h2.data[row - (x.size() / 2)][col - (x.size() / 2)];
            }
        }

        return prod;
    }

}