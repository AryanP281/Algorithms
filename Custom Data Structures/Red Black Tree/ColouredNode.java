package CustomDataStructures.BinaryTree;

import javax.swing.text.AttributeSet.ColorAttribute;

public class ColouredNode<K, V> 
{
    public K key; //The key of the node
    public V value; //The data stored in the node
    public ColouredNode<K,V> parent; //The parent of the node
    public ColouredNode<K,V> leftChild; //The left child of the node
    public ColouredNode<K,V> rightChild; //The right child of the node
    public boolean isRed; //True if the node is red and false if the node is black

    public ColouredNode(K key, V value, boolean isRed)
    {
        this.key = key;
        this.value = value;
        this.isRed = isRed;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public ColouredNode(K key, V value, ColouredNode<K,V> parent, ColouredNode<K,V> leftChild, ColouredNode<K,V> rightChild, boolean isRed)
    {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.isRed = isRed;
    }
}