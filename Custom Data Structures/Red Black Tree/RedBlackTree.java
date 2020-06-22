package CustomDataStructures.BinaryTree;

import CustomDataStructures.Compare.Compare;
import CustomDataStructures.KeyValuePair.*;

public class RedBlackTree<K,V>
{
    private ColouredNode<K,V> root; //The root of the tree
    private Compare<K> cmp; //The class to be used for comparing the elements

    public RedBlackTree(Compare<K> compareInterface)
    {
        /*Initializes the tree*/
        
        root = null;
        cmp = compareInterface;
    }

    public void insert(K key, V value)
    {
        /*Adds a new node to the tree
        key = the key of the new node
        value = the value of the new node
        */

        ColouredNode<K,V> newNode = new ColouredNode<K,V>(key, value, true); //Initializing the node to be added

        //Checking if the tree is empty
        if(root == null)
        {
            //Case 1 : Parent is null i.e new node is root node
            newNode.isRed = false; //Root node has to be black
            this.root = newNode;
            return;
        }

        KeyValuePair<ColouredNode<K,V>,Integer> parentInfo = getParent(key); //Getting the parent node that the new node should be added to
        ColouredNode<K,V> parent = parentInfo.key; //The parent node

        //Adding the new node
        if(parentInfo.value > 0)
        {
            parent.rightChild = newNode;
            newNode.parent = parent;
        }
        else
        {
            parent.leftChild = newNode;
            newNode.parent = parent;
        }
        
        if(parent.isRed)
        {
            //Case 3 : Parent is Red
            while(parent.isRed)
            {                  
                //Getting the uncle node
                ColouredNode<K,V> uncleNode = (parent.parent.leftChild == parent) ? parent.parent.rightChild : parent.parent.leftChild;

                //Case 3.1 : Uncle is Red
                if(uncleNode != null && uncleNode.isRed)
                {
                    parent.parent.isRed = true; //Making the grandparent node red
                    uncleNode.isRed = false; //Making the uncle node black
                    parent.isRed = false; //Making the parent node red
                }
                //Case 3.2 : Uncle is black
                else
                {
                    //Case 3.2.1 : Parent is right child and new node is right child
                    if(parent.parent.rightChild == parent && parent.rightChild == newNode)
                    {
                        leftRotation(parent.parent); //Performing left rotation at grandparent
                        parent.isRed = false; //Making the parent black
                        parent.leftChild.isRed = true; //Making the sibling (old grandparent) red
                        break;
                    }
                    //Case 3.2.2 : Parent is right child and new node is left child
                    else if(parent.parent.rightChild == parent && parent.leftChild == newNode)
                    {
                        rightRotation(parent); //Performing right rotation at parent, so that case 3.2.1 is obtained
                        parent = parent.parent; 
                        
                        //Performing the operations required for case 3.2.1
                        leftRotation(parent.parent); //Performing left rotation at grandparent
                        parent.isRed = false; //Making the parent black
                        parent.leftChild.isRed = true; //Making the sibling (old grandparent) red
                        break;
                    }
                    //Case 3.2.3 : Parent is left child and new node is left child
                    else if(parent.parent.leftChild == parent && parent.leftChild == newNode)
                    {
                        rightRotation(parent.parent); //Performing right rotation at grandparent
                        parent.isRed = false; //Making the parent black
                        parent.rightChild.isRed = true; //Making the sibling (earlier grandparent) red
                        break;
                    }
                    //Case 3.2.4 : Parent is left child and new node is right child
                    else if(parent.parent.leftChild == parent && parent.rightChild == newNode)
                    {
                        leftRotation(parent); //Performing left rotation at parent to obtain case 3.2.3
                        parent = parent.parent;

                        //Performing the operations required for case 3.2.3
                        rightRotation(parent.parent); //Performing right rotation at grandparent
                        parent.isRed = false; //Making the parent black
                        parent.rightChild.isRed = true; //Making the sibling (earlier grandparent) red
                        break;
                    }

                }

                //Checking if the new parent surpasses the root
                if(parent.parent.parent == null)
                {
                    parent.parent.isRed = false; //Making the root black
                    break;
                } 
                newNode = parent.parent;
                parent = parent.parent.parent;
            }
        }

    }

    private KeyValuePair<ColouredNode<K,V>,Integer> getParent(K key)
    {
        /*Returns the parent for the new node.
        key = the key of the new node
        */

        ColouredNode<K,V> currentNode = root; //The node currently traversed
        Integer position = 0;

        while(currentNode != null)
        {
            if(cmp.compare(key, currentNode.key) > 0)
            {
                position = 1;
                if(currentNode.rightChild == null) break;

                currentNode = currentNode.rightChild;
            }
            else
            {
                position = -1;
                if(currentNode.leftChild == null) break;

                currentNode = currentNode.leftChild;
            }
        }

        return new KeyValuePair<ColouredNode<K,V>,Integer>(currentNode, position);
    }

    private void leftRotation(ColouredNode<K,V> node)
    {
        /*Performs left rotation on the given node
        node = the node at which to perform the left rotation*/

        ColouredNode<K,V> childToRotate = node.rightChild;

        childToRotate.parent = node.parent;
        if(node.parent != null)
        {
            if(node.parent.rightChild == node) node.parent.rightChild = childToRotate;
            else node.parent.leftChild = childToRotate;
        }
        else
            root = childToRotate;

        ColouredNode<K,V> rotatedChildsLeftChild = childToRotate.leftChild;
        node.rightChild = rotatedChildsLeftChild;
        if(rotatedChildsLeftChild != null) rotatedChildsLeftChild.parent = node;
        childToRotate.leftChild = node;
        node.parent = childToRotate;
    }

    private void rightRotation(ColouredNode<K,V> node)
    {
        /*Performs right rotation at the given node
        node = the node at which to perform the right rotation*/

        ColouredNode<K,V> childToRotate = node.leftChild;

        childToRotate.parent = node.parent;
        if(node.parent != null)
        {
            if(node.parent.rightChild == node) node.parent.rightChild = childToRotate;
            else node.parent.leftChild = childToRotate;
        }
        else
            root = childToRotate;

        ColouredNode<K,V> rotatedChildsRightChild = childToRotate.rightChild;
        node.leftChild = rotatedChildsRightChild;
        if(rotatedChildsRightChild != null)rotatedChildsRightChild.parent = node;
        childToRotate.rightChild = node;
        node.parent = childToRotate;
    }

    public V getValue(K key)
    {
        /*Returns the value of the node with the given key
        key = the key to search for
        Returns null if key is not found*/
        
        ColouredNode<K,V> node = getNode(key);
        return (node == null ? null : node.value);
    }

    public void remove(K key)
    {
        /*Removes the node with given key from the tree
        key = the key to remove from the tree*/

        ColouredNode<K,V> node = getNode(key); //Getting the node

        //Looking for the successor i.e node with only 1 or no child
        if(node.leftChild != null && node.rightChild != null)
        {
            ColouredNode<K,V> predecessor = getPredecessor(node);
            //Swapping the node and its predecessor
            node.key = predecessor.key;
            node.value = predecessor.value;
            node = predecessor;
        }

        ColouredNode<K,V> child = node.leftChild == null ? node.rightChild : node.leftChild; //Child of the node to be deleted

        //Case 1 : Node is red
        if(node.isRed)
        {
            if(node.parent.leftChild == node) node.parent.leftChild = child;
            else node.parent.rightChild = child;
            
            if(child != null) child.parent = node.parent;
        }
        else
        {
            //Black node

            //Case 2 : Black node with red child
            if(!isBlack(child))
            {
                //Replacing the node with its child
                if(node.parent != null)
                {
                    if(node.parent.leftChild == node) node.parent.leftChild = child;
                    else node.parent.rightChild = child;
                }
                else root = child;
                child.parent = node.parent;

                child.isRed = false; //Making the child node black
            }
            else
            {
                //Case 3 : Black node with black or null child
                ColouredNode<K,V> doubleBlackNode = node; //Deleting a black node creates a double black node

                while(doubleBlackNode != null)
                {
                    //Case 3.1 : Double Black node is root
                    if(root == doubleBlackNode)
                    {
                        //The double black node is the node to be deleted
                        if(doubleBlackNode == node) root = null;
                        else
                        {
                            //The double black node has been propogated
                            doubleBlackNode.isRed = false; //Making the root black
                        }

                        break; //Breaking the loop as double black has been eliminated
                    }

                    ColouredNode<K,V> sibling = (doubleBlackNode.parent.leftChild == doubleBlackNode) ? doubleBlackNode.parent.rightChild :
                        doubleBlackNode.parent.leftChild; //The sibling of the double black node

                    //Sibling is black
                    if((sibling != null && !sibling.isRed))
                    {
                        //Getting the inner child of the black sibling
                        ColouredNode<K,V> innerChild = (sibling.parent.rightChild == sibling) ? sibling.leftChild : sibling.rightChild;
                        //Getting the outer child of the sibling
                        ColouredNode<K,V> outerChild = (sibling.leftChild == innerChild) ? sibling.rightChild : sibling.leftChild;
                        
                        //Case 3.2 : Sibling is black and both of its children are black
                        if(isBlack(innerChild) && isBlack(outerChild))
                        {
                            sibling.isRed = true; //Making sibling red

                            //Removing the double black node
                            if(doubleBlackNode == node)
                            {
                                ColouredNode<K,V> replacementNode = doubleBlackNode.leftChild == null ? doubleBlackNode.rightChild : doubleBlackNode.leftChild;
                                if(doubleBlackNode.parent.leftChild == doubleBlackNode) doubleBlackNode.parent.leftChild = replacementNode;
                                else doubleBlackNode.parent.rightChild = replacementNode;

                                if(replacementNode != null) replacementNode.parent = doubleBlackNode.parent;
                            }
                            else
                                doubleBlackNode.isRed = false;
                            
                            //Propogating double black to parent
                            if(doubleBlackNode.parent.isRed)
                            {
                                doubleBlackNode.parent.isRed = false;
                                break; //Breaking the loop as double black has been eliminated
                            }
                            else 
                            {
                                doubleBlackNode = doubleBlackNode.parent;
                                continue; //Double black has not been eliminated, only its position has changed
                            }
                        }

                        //Case 3.4 : DB’s sibling is black and only inner child of sibling is red
                        if(!isBlack(innerChild) && isBlack(outerChild))
                        {
                            //Swapping the colours of sibling and its inner child
                            sibling.isRed = true;
                            innerChild.isRed = false;

                            //Rotation at sibling opposite to double black
                            rotateOppositeTo(sibling, doubleBlackNode);

                            //The sibling and inner child are now exchanged
                            outerChild = sibling;
                            sibling = innerChild;
                        }

                        //Case 3.5 : DB’s sibling is black and the sibling’s outer child is red
                        if(!isBlack(outerChild))
                        {
                            //Swapping the colours of parent and sibling
                            sibling.isRed = sibling.parent.isRed;
                            sibling.parent.isRed = false;

                            //Rotation at parent in double black direction
                            rotateTowards(sibling.parent, doubleBlackNode);

                            //Making the outer child black
                            outerChild.isRed = false;

                            //Removing double black
                            if(doubleBlackNode == node)
                            {
                                ColouredNode<K,V> replacementNode = doubleBlackNode.leftChild == null ? doubleBlackNode.rightChild : doubleBlackNode.leftChild;
                                if(doubleBlackNode.parent.leftChild == doubleBlackNode) doubleBlackNode.parent.leftChild = replacementNode;
                                else doubleBlackNode.parent.rightChild = replacementNode;

                                if(replacementNode != null) replacementNode.parent = doubleBlackNode;
                            }
                            else 
                            {
                                doubleBlackNode.isRed = false;
                            }

                            break; //Breaking the loop as double black has been eliminated
                        }

                    }

                    //Case 3.3 : Sibling is red
                    if(!isBlack(sibling))
                    {
                        //Swapping the colours of the parent and the sibling
                        doubleBlackNode.parent.isRed = true;
                        sibling.isRed = false;

                        //Rotation at parent in direction of double black
                        rotateTowards(doubleBlackNode.parent,doubleBlackNode);

                        continue; //Double black has not been eliminated, only its position has changed
                    }
                }
            }
        }

    }

    private ColouredNode<K,V> getNode(K key)
    {
        /*Finds and returns the node with the given key. Returns null if key does not exist in tree*/

        ColouredNode<K,V> currentNode = this.root;

        while(currentNode != null)
        {
            int comparison = cmp.compare(key, currentNode.key);
            if(comparison > 0) currentNode = currentNode.rightChild;
            else if(comparison < 0) currentNode = currentNode.leftChild;
            else return currentNode;
        }

        return null;
    }

    private ColouredNode<K,V> getPredecessor(ColouredNode<K,V> node)
    {
        /*Finds and returns the predecessor of the given node*/

        ColouredNode<K,V> currentNode = node.leftChild;
        while(currentNode != null)
        {
            if(currentNode.rightChild == null) break;
            currentNode = currentNode.rightChild;
        }

        return currentNode;
    }

    private boolean leftChildIsBlack(ColouredNode<K,V> node)
    {
        /*Returns true if the left child of the given node is to be considered black*/

        return node.leftChild == null || !node.leftChild.isRed;
    }

    private boolean rightChildIsBlack(ColouredNode<K,V> node)
    {
        /*Returns true if the right child of the given node is to be considered black*/

        return node.rightChild == null || !node.rightChild.isRed;
    }

    private boolean isBlack(ColouredNode<K,V> node)
    {
        /*Returns true if the given node is to be considere black*/

        return node == null || !node.isRed;
    }

    private void rotateTowards(ColouredNode<K,V> pivot, ColouredNode<K,V> dir)
    {
        /*Rotates the pivot in the direction of the given node
        pivot = the node to perform rotation at
        dir = the node in whose direction rotation is to be performed */

        int comparison = cmp.compare(pivot.key, dir.key);
        if(comparison > 0) leftRotation(pivot);
        else if(comparison < 0) rightRotation(pivot);
        else 
        {
            comparison = cmp.compare(pivot.key, dir.parent.key);
            if(comparison > 0) leftRotation(pivot);
            else if(comparison < 0) rightRotation(pivot);
            else
            {
                if(pivot.leftChild == dir) leftRotation(pivot);
                else rightRotation(pivot);
            }
        }

    }

    private void rotateOppositeTo(ColouredNode<K,V> pivot, ColouredNode<K,V> dir)
    {
        /*Rotates the pivot in the opposite to the given node
        pivot = the node to perform rotation at
        dir = the node in whose opposite direction rotation is to be performed */

        int comparison = cmp.compare(pivot.key, dir.key);
        if(comparison > 0) rightRotation(pivot);
        else if(comparison < 0) leftRotation(pivot);
        else 
        {
            comparison = cmp.compare(pivot.key, dir.parent.key);
            if(comparison > 0) rightRotation(pivot);
            else if(comparison < 0) leftRotation(pivot);
            else
            {
                if(pivot.leftChild == dir) rightRotation(pivot);
                else leftRotation(pivot);
            }
        }

    }
}
