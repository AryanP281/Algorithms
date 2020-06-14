package CustomDataStructures.Stack;

import java.util.ArrayList;

public class Stack<T>
{
    private final int capacity; //The capacity of the stack
    private int size; //The current size of the stack
    private int top; //A pointer to the top element of the stack
    private ArrayList<T> data; //The data in the stack

    public Stack(int capacity)
    {
        /*Initializes the data structure.
        capacity = the max number of the elements that can be stored in the stack
        */
        
        this.capacity = capacity; 
        this.size = this.top = 0;

        data = new ArrayList<T>(capacity);
    }

    public boolean isFull()
    {
        /*Tells whether the stack is full*/

        return this.size == this.capacity;
    }

    public boolean isEmpty()
    {
        /*Tells whether the stack is empty*/

        return this.size == 0;
    }

    public void push(T e)
    {
        /*Adds an element to the top of the stack.
        e = the element to be added
        */

        //Checking if the stack is full
        if(isFull()) throw new ArrayIndexOutOfBoundsException("Stack full. Cannot add new element to stack.");

        if(data.size() > this.top)
            data.set(top++, e);
        else
        {
            data.add(e);
            ++top;
        }   
        
        ++this.size;
    }

    public T pop()
    {
        /*Removes and returns the element at the top of the stack*/

        if(isEmpty()) throw new ArrayIndexOutOfBoundsException("Stack Empty. Cannot pop element.");

        this.size--;
        return this.data.get(--this.top);
    }

    public T peek()
    {
        /*Returns the element at the top of the stack, without removing it from the stack*/

        return this.data.get(top-1);
    }

    public int getSize()
    {
        /*Returns the number of elements currently present in the stack*/

        return this.size;
    }

    public int getCapacity()
    {
        /*Returns the capacity of the stack*/

        return this.capacity;
    }

}