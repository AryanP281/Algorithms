package CustomDataStructures.Heap;

import java.util.*;
import CustomDataStructures.Compare.Compare;


public class MinHeap<T>
{
    private int size; //The current size of the heap
    private int capacity; //The max number of elements that can be stored in the heap
    private T data[]; //The elements in the heap
    private Compare<T> cmp; //The interface to be used to compare the elements
    final T MIN_LIMIT; //The least possible value that an element can have
    private HashMap<T, Integer> indices; //Stores the indices of the keys to allow constant time access
    

    public MinHeap(Compare<T> compareWith, T minLimit)
    {
        /*Initializes the heap.*/

        capacity = 100;
        size = 0;
        cmp = compareWith;
        MIN_LIMIT = minLimit;
        indices = new HashMap<>(capacity);
        data = (T[])new Object[capacity];
    }

    public MinHeap(int capacity, Compare<T> compareWith, T minLimit)
    {
        /*Initializes the heap.
        capacity = the capacity of the heap*/

        this.capacity = capacity;
        size = 0;
        cmp = compareWith;
        MIN_LIMIT = minLimit;
        indices = new HashMap(capacity);
        data = (T[])new Object[capacity];
    }

    private int getParentIndex(int childIndex)
    {
        /*Returns the index of the parent in the array*/
        
        return (childIndex - 1) / 2;
    }

    private int getLeftChildIndex(int parentIndex)
    {
        /*Returns the index of the left child in the array*/

        return (2*parentIndex) + 1;
    }

    private int getRightChildIndex(int parentIndex)
    {
        /*Returns the index of the left child in the array*/

        return (2*parentIndex) + 2;
    }

    private boolean hasLeftChild(int parentIndex)
    {
        /*Tells whether the node has a left child*/

        return (this.getLeftChildIndex(parentIndex) < this.size);
    }

    private boolean hasRightChild(int parentIndex)
    {
        /*Tells whether the node has a right child*/

        return (this.getRightChildIndex(parentIndex) < this.size);
    }

    private boolean hasParent(int childIndex)
    {
        /*Tells whether the node has a parent*/

        if(childIndex == 0) return false;

        return (this.getParentIndex(childIndex) >= 0);
    }

    private T getLeftChild(int parentIndex)
    {
        /*Returns the left child of the parent*/

        return data[getLeftChildIndex(parentIndex)];
    }

    private T getRightChild(int parentIndex)
    {
        /*Returns the right child of the parent*/

         return data[getRightChildIndex(parentIndex)];
    }

    private T getParent(int childIndex)
    {
        /*Returns the parent of the child*/

        return data[getParentIndex(childIndex)];
    }

    private void swap(int i, int j)
    {
        /*Swaps the elements at the two positions*/

        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void ensureExtraCapacity()
    {
        if(this.size == this.capacity)
        {
            data = Arrays.copyOf(data, capacity*2);
            capacity *= 2;
        }
    }

    public T peek()
    {
        /*Returns the root element without removing it from the heap*/

        if(this.size == 0) throw new IllegalStateException();

        return data[0];
    }

    public void BuildHeap(T arr[])
    {
        /*Builds the heap from the given array*/

        //Initializing the data array
        this.data = arr.clone();
        this.capacity = arr.length;
        this.size = arr.length;
        this.indices.clear();
        
        //Initalizing the indices
        for(int a = 0; a < arr.length;++a)
        {
            indices.put(data[a], a);
        }

        for(int a = (arr.length / 2) - 1; a >= 0; --a)
        {
            heapify(a);
        }
    }

    private void heapify(int i)
    {
        //Getting the child of the node having the min value
        int smallestChild = getLeftChildIndex(i);
        if(hasRightChild(i))
            smallestChild = cmp.compare(getLeftChild(i), getRightChild(i)) > 0 ? getRightChildIndex(i) : getLeftChildIndex(i);
        
        while(cmp.compare(data[i], data[smallestChild]) > 0)
        {
            //Updating the hash map
            indices.replace(data[i], smallestChild);
            indices.replace(data[smallestChild], i);
            
            swap(i, smallestChild); 
            i = smallestChild;

            //Checking if leaf node
            if(!hasLeftChild(i)) break;

            if(hasRightChild(i))
                smallestChild = cmp.compare(getLeftChild(i), getRightChild(i)) > 0 ? getRightChildIndex(i) : getLeftChildIndex(i);
            else
                smallestChild = getLeftChildIndex(i);

        }
    }

    public T poll()
    {
        /*Returns the root and removes it from the heap*/

        if(this.size == 0) throw new IllegalStateException();

        T root = data[0]; //Saving the root as it is to be returned
        indices.remove(root); //Removing the root from the map of key indices
        data[0] = data[size-1]; //Moving the last element to the root
        size--; //Decreasing the size as an element has been removed 
        heapifyDown(); //Re-heapifying

        return root;
    }

    private void heapifyDown()
    {
        int i = 0; //The current location of the root element
        
        while(hasLeftChild(i))
        {
            //Getting the child node having min value
            int smallestChild = cmp.compare(getLeftChild(i), getRightChild(i)) < 0 ? getLeftChildIndex(i) : getRightChildIndex(i);

            if(cmp.compare(data[i], data[smallestChild]) < 0)
                break;
            
            indices.replace(data[i], smallestChild); //Updating the position of the current key 
            indices.replace(data[smallestChild], i); //Updating the position of the smallest child
            swap(i, smallestChild);
            i = smallestChild;
        } 
    }

    public void push(T e)
    {
        /*Adds a new element to the heap
        e = the element to be added*/

        ensureExtraCapacity(); //Making sure the array has space to hold the element
        this.size++;
        data[size - 1] = e; //Adding the element to the bottom of the heap
        indices.put(data[size-1], size-1);
        heapifyUp(this.size - 1); //Re-heapifying
    }

    private void heapifyUp(int start)
    {
        int i = start; //Starting with the last element

        while(hasParent(i) && cmp.compare(data[i], getParent(i)) < 0)
        {
            int parentIndex = getParentIndex(i);
            indices.replace(data[i], parentIndex); //Updating the position of the current key
            indices.replace(data[parentIndex], i); //Updating the position of the parent key
            swap(i, parentIndex);
            i = parentIndex;
        }
    }

    public void changeKey(T key, T newVal)
    {
        /*Changes the value of the given key.
        index = the index of the key in the array
        newVal = the new value to be assigned to the key
        */
        
        if(this.size == 0) throw new IllegalStateException();

        int index = getKeyIndex(key); //Getting the index of the key
        indices.remove(data[index]); //Removing the key from the hashmap
        data[index] = newVal; //Updating the key
        indices.put(data[index], index); //Updating the hashmap

        //Checking heap needs to be restored
        if(cmp.compare(data[index], getParent(index)) < 0)
            heapifyUp(index); //Restoring the heap
    }

    public int getKeyIndex(T key)
    {
        /*Returns the index of the key in the array
        key = the key whose index is to be found*/

        Integer index = indices.get(key);

        if(index == null) throw new ArrayIndexOutOfBoundsException();
        
        return index;
    }

    public void delete(T key)
    {
        /*Deletes the given key from the index
        key = the key to be deleted
        */

        //Decreasing the value of the key to the min possibility
        changeKey(key, MIN_LIMIT);

        //Removing the key by popping the root
        this.poll();
    }

    public int getSize()
    {
        /*Returns the number of elements in the heap*/

        return this.size;
    }

}
