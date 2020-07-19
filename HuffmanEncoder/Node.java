
import java.util.ArrayList;

class Node 
{
    private char character; //The character represented by the node
    Node leftChild; //The left child of the node
    Node rightChild; //The right child of the node
    private int frequency; //The frequency of the node

    Node()
    {
        this.character = '\0';
        this.leftChild = null;
        this.rightChild = null;
        this.frequency = 0;
    }

    void setChar(char ch, int freq)
    {
        /*Sets the character of the node to the character with the given frequency to the node*/

        character = ch;
        frequency += freq;
    }

    int getNodeFrequency()
    {
        /*Returns the frequency of the node*/

        return frequency;
    }

    char getCharacter()
    {
        /*Returns the character stored in the node*/

        return character;
    }

    void setFrequency(int newFreq)
    {
        /*Sets the value of the node frequency. Intended for empty nodes which arent leaf nodes*/

        this.frequency = newFreq;
    }

}