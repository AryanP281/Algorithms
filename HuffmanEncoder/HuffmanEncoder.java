
import java.util.*;

import CustomDataStructures.Heap.MinHeap;
import CustomDataStructures.Compare.Compare;

class HuffmanEncoder
{
    private static Compare<Node> cmp = new Compare<Node>()
    {
        @Override
        public int compare(Node n1, Node n2)
        {
            return Integer.compare(n1.getNodeFrequency(), n2.getNodeFrequency());
        }

    }; //The interface to be used for comparing nodes

    HashMap<Character,Integer> charFreqs;
    Node encodingTreeRoot; //The root of the encoding tree

    HuffmanEncoder()
    {
    }
    
    HashMap<Character, ArrayList<Boolean>> encode(HashMap<Character,Integer> charFreqs)
    {
        /*Encodes the given characters using Huffman encoding. Returns a hashmap containing the 
        characters and the corresponding encoding bits*/

        this.charFreqs = charFreqs;
        
        MinHeap<Node> heap = buildHeap(); //Building the heap

        while(heap.getSize() > 1)
        {
            Node nodes[] = {heap.poll(), heap.poll()}; //Getting the nodes to merge i.e the nodes with min frequencies

            //Creating the meta node
            Node metaNode = new Node();
            metaNode.leftChild = nodes[0];
            metaNode.rightChild = nodes[1];
            metaNode.setFrequency(nodes[0].getNodeFrequency() + nodes[1].getNodeFrequency());

            //Adding the meta node to the heap
            heap.push(metaNode);
        }

        HashMap<Character, ArrayList<Boolean>> encodings = new HashMap<Character, ArrayList<Boolean>>(); //The characters and their corresponding encodings in bits
        encodingTreeRoot = heap.poll();
        traverseEncodingTree(encodingTreeRoot, encodings, new ArrayList<Boolean>());; //Getting the character encodings

        return encodings;
    }

    private MinHeap<Node> buildHeap()
    {
        /*Builds and returns a heap containing the given characters*/

        MinHeap<Node> heap = new MinHeap<Node>(cmp, new Node()); //The heap for storing the nodes
        Set<Character> chars = charFreqs.keySet(); //Gets the characters to be encoded
        //Populating the heap with character nodes
        for(char ch : chars)
        {
            //Creating a new node containing the character
            Node node = new Node();
            node.setChar(ch, charFreqs.get(ch));

            //Adding the node to the heap
            heap.push(node);
        }

        return heap;
    }

    private void traverseEncodingTree(Node node, HashMap<Character, ArrayList<Boolean>> encodings, ArrayList<Boolean> lastEncoding)
    {
        /*Traverses the encoding tree (dfs) and sets the character encoding bits*/
        
        //Checking if the node is a character leaf node
        if(node.getCharacter() != '\0')
        {
            encodings.put(node.getCharacter(), lastEncoding);
            return;
        }

        ArrayList<Boolean> subtreeEncoding = (ArrayList<Boolean>)lastEncoding.clone();
        subtreeEncoding.add(false); //The starting encoding for the left subtree
        traverseEncodingTree(node.leftChild, encodings, subtreeEncoding); //Traversing the left subtree
        subtreeEncoding = (ArrayList<Boolean>)lastEncoding.clone(); 
        subtreeEncoding.add(true); //The starting encoding for the right subtree
        traverseEncodingTree(node.rightChild, encodings, subtreeEncoding); //Traversing the right subtree
    }

    String decode(ArrayList<Boolean> bits)
    {
        /*Decodes the given sequence of bits and returns the string*/

        StringBuilder decodedStr = new StringBuilder();
        int bitToRead = 0; //The bit to read at the current step
        
        while(bitToRead < bits.size())
        {
            Node currentNode = this.encodingTreeRoot;
            while(currentNode.getCharacter() == '\0')
            {
                currentNode = bits.get(bitToRead++) == false ? currentNode.leftChild : currentNode.rightChild;
            }

            decodedStr.append(currentNode.getCharacter());
        }

        return decodedStr.toString();
    }

}