package CustomDataStructures.Compare;

public class DefaultCompare<T extends Comparable> implements Compare<T>
{

    @Override
    public int compare(T t1, T t2)
    {
        return t1.compareTo(t2);
    }
}