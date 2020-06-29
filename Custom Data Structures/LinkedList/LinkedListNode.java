package CustomDataStructures.LinkedList;

public class LinkedListNode<V> {
    
    public V data; //The data stored in the node
    public LinkedListNode<V> next; //Pointer to the next node

    public LinkedListNode()
    {
        data = null;
        next = null;
    }

    public LinkedListNode(V data, LinkedListNode<V> next)
    {
        this.data = data;
        this.next = next;
    }

}