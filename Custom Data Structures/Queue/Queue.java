package CustomDataStructures.Queue;

import java.util.*;

public class Queue<T>
{
    private int front; //The front of the queue i.e the position at which elements are removed from the queue
    private int rear; //The rear of the queue i.e the position at which elements are added to the queue
    private int size; //The size of the array i.e the the number of elements currently present in the queue
    private int capacity; //The max number of elements that the queue can contain
    private ArrayList<T> data; //An array list for holding the queue elements

    public Queue(int capacity)
    {
        /*Initializes the queue.
        capacity = the max number of elements that the queue can contain*/

        //Initializing queue
        this.front = 0;
        this.rear = 0;
        this.size = 0;
        this.capacity = capacity;
        this.data = new ArrayList<T>(capacity);

    }

    public boolean isFull()
    {
        /*Returns whether the queue is full*/
        
        return (this.size == this.capacity);
    }

    public boolean isEmpty()
    {
        /*Returns whether the queue is empty*/
        
        return (this.size == 0);
    }

    public void enqueue(T e)
    {
        /*Adds the element to the queue if the queue is not full.
        e = the element to be added*/

        if(isFull()) //Checking if the queue is full
            return;

        if(this.data.size() == this.capacity) 
        {
            this.data.set(rear, e); //Adding the element
            this.rear = (this.rear+1) % this.capacity; //Repositioning the rear pointer
        }
        else this.data.add(e);
        
        this.size++;
    }

    public T dequeue()
    {
        /*Returns the element at the front of the queue and removes it from the queue*/

        if(isEmpty()) //Checking if the queue is empty
            return null;

        T e = this.data.get(front); //The element to be popped from the queue
        this.size--;
        this.front = (this.front+1) % this.capacity; //Repositioning the front pointer

        return e;
    }

    public T front()
    {
        /*Returns the element at the front of the queue, without popping it from the queue*/

        if(isEmpty()) //Checking if the queue is empty
            return null;

        return this.data.get(this.front);
    }

    public T rear()
    {
        /*Returns the element at the rear of the queue*/

        if(isEmpty()) //Checking if the queue is empty
            return null;

        return this.data.get(this.rear);
    }

    public int getCapacity()
    {
        /*Returns the capacity of the queue*/

        return this.capacity;
    }

    public int getSize()
    {
        /*Returns the current size of the queue*/

        return this.size;
    }
}