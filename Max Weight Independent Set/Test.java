public class Test {
    
    public static void main(String args[])
    {
        int vertices[] = {7, 0, 3, 2, 1 ,6};

        MaxWeightIndependentSet mwis = new MaxWeightIndependentSet();

        System.out.println(mwis.getSet(vertices).element2);
    }

}