
import java.util.*;

class Test 
{
    public static void main(String args[])
    {
        HashMap<Character,Integer> charFreqs = new HashMap<Character,Integer>();
        charFreqs.put('a', 5);
        charFreqs.put('b', 9);
        charFreqs.put('c', 12);
        charFreqs.put('d', 13);
        charFreqs.put('e', 16);
        charFreqs.put('f', 45);

        HuffmanEncoder he = new HuffmanEncoder();
        HashMap<Character, ArrayList<Boolean>> encoding = he.encode(charFreqs);

        Set<Character> chars = encoding.keySet();
        int totalBits = 0;
        for(char ch : chars)
        {
            System.out.print(ch + " = ");
            for(boolean bit : encoding.get(ch))
            {
                System.out.print(bit == true ? 1 : 0);
            }
            System.out.print("\n");
            totalBits += charFreqs.get(ch) * encoding.get(ch).size();
        }

        System.out.println("\nTotal encoding bits = " + totalBits);

        ArrayList<Boolean> encodedMsg = new ArrayList<Boolean>(Arrays.asList(new Boolean[]{true,true,false,false,true,false,false,false,true,false,true}));
        System.out.println(he.decode(encodedMsg));
    }    
}