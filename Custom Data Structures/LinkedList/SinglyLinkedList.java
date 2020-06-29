package CustomDataStructures.LinkedList;

import CustomDataStructures.Compare.Compare;

public class SinglyLinkedList<V> 
{
    private LinkedListNode<V> head; //The head node of the linked list
    private LinkedListNode<V> tail; //The tail node of the linked list
    private Compare<V> cmp; //The interface to be used for comparing values

    public SinglyLinkedList(Compare<V> comparison) 
    {
        /*Initializes the linked list.
        comparison = the interface to be used for comparing node values*/

        cmp = comparison;
        head = null;
        tail = null;
    }

    public void push(V value)
    {
        /*Adds a new node to the head of the list.
        value = the data to store in the new node */

        insertAfter((LinkedListNode<V>)null, value); //Adding the new node
    }

    public void insertAfter(LinkedListNode<V> prevNode, V value)
    {
        /*Creates a new node and inserts it after the given node
        prevNode = the node that should point to the new node,
        value = the data to store in the new node*/
 
        LinkedListNode<V> newNode = new LinkedListNode<V>(value, (prevNode != null) ? prevNode.next : this.head); //The new node to be added
        if(prevNode != null)
            prevNode.next = newNode; //Adding the new node after the given node
        else this.head = newNode; //As the previous node is null, the new node is the head of the list

        //Checking if the new node is the tail
        if(newNode.next == null) this.tail = newNode;

    }

    public void insertAfter(V prevNodeValue, V value)
    {
        /*Creates a new node and inserts it after the node with the given value
        prevNode = the node that should point to the new node,
        value = the data to store in the new node*/

        LinkedListNode<V> prevNode = getNode(prevNodeValue); //Getting the node that should preceed the new node
        if(prevNode == null) throw new IllegalArgumentException("Node with given value not found");

        insertAfter(prevNode, value); //Adding the new node
    }

    private LinkedListNode<V> getNode(V value)
    {
        /*Returns the node with the given value
        value = the value to search for
        RETURNS null if a node with the given value is not found*/

        LinkedListNode<V> currentNode = this.head;
        while(currentNode != null)
        {
            if(cmp.compare(value, currentNode.data) == 0) break;
            
            else currentNode = currentNode.next;
        }

        return currentNode;
    }

    public void append(V value)
    {
        /*Creates and new node and adds it to the end of the list
        value = the data to store in the new node*/

        insertAfter(this.tail, value); //Adding the new node at the tail
    }

    public V getValueAt(int index)
    {
        /*Returns the value of the node at the given index. The indices start from 0 (the head)
        index = the index of the node
        returns null if node with given value does not exits
        */

        LinkedListNode<V> node = getNodeAt(index);

        return node == null ? null : node.data;
    }

    private LinkedListNode<V> getNodeAt(int index)
    {
        /*Returns the node at the given index. Returns null if node not found*/

        LinkedListNode<V> currentNode = this.head;
        boolean nodeFound = false;
        for(int a = 0; a <= index && currentNode != null; ++a, currentNode = currentNode.next)
        {
            if(a == index)
            {
                nodeFound = true;
                break;
            }
        }

        return nodeFound ? currentNode : null;
    }

    public boolean exists(V value)
    {
        /*Returns true if a node with the given value exists in the linked list.
        value = the value to search for */

        return getNode(value) != null;
    }

    public void remove(V value)
    {
        /*Removes the node with the given value from the list
        value = the value of the node to be removed*/

        LinkedListNode<V> node = getNode(value); //The node to be removed
        if(node == null) throw new IllegalArgumentException("Node not found");

        //Searching for the previous node
        if(this.head != node) //Head node doesent have a predecessor
        {
            LinkedListNode<V> prevNode = this.head;
            while(prevNode.next != node)
            {
                prevNode = prevNode.next;
            }

            //Removing the node
            prevNode.next = node.next;

            //Checking if the tail has been removed
            if(node.next == null) this.tail = prevNode;
        }
        else 
        {
            this.head = node.next;

            //Checking if the tail has been removed
            if(node.next == null) this.tail = null;
        }
    }

    public void removeAt(int index)
    {
        /*Removes the node at the given position*/

        LinkedListNode<V> node = getNodeAt(index); //The node to be deleted
        if(node == null) throw new IllegalArgumentException("Node not found");

        //Getting the previous node
        LinkedListNode<V> prevNode = getNodeAt(index - 1);
        if(prevNode == null) //Head node
            this.head = node.next;
        else prevNode.next = node.next;

        //Checking if tail has been removed
        if(node.next == null) this.tail = prevNode == null ? null : prevNode;
    }
}