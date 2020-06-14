package CustomDataStructures.Compare;

public interface Compare<T>
{
    public int compare(T t1, T t2); //Should return -1 if t1 < t2, 0 if t1 == t2 or 1 if t1 > t2
}