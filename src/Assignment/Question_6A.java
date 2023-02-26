package Assignment;

/**Implement Huffman encoding and decoding.**/
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Question_6A {
    /**The lossless data compression technology known as Huffman coding is used to compress text data.**/
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        int frequency;
        char character;
        HuffmanNode left, right;

        /**To represent a node in the Huffman tree, the program first builds a HuffmanNode class, with each node
         * containing a character, frequency, left and right child nodes. The Comparable interface is also implemented
         * by the class to support sorting according on node frequency.**/
        public HuffmanNode(int frequency, char character) {
            this.frequency = frequency;
            this.character = character;
        }

        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            this.frequency = left.frequency + right.frequency;
            this.left = left;
            this.right = right;
        }

        /**Comparison is based on the frequency of the nodes**/
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }

    /**A MapCharacter, Integer> containing the frequency of each character in the input string is passed to the buildTree function.
     * Based on the frequency of each character, a Huffman tree is built using a priority queue. When there is only one node left,
     * which is the root of the Huffman tree, the procedure polls two nodes from the queue, produces a new node with the sum of
     * their frequencies, and adds it back to the queue. The root node is returned by the procedure.**/
    public static HuffmanNode buildTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (char c : frequencyMap.keySet()) {
            pq.add(new HuffmanNode(frequencyMap.get(c), c));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            pq.add(new HuffmanNode(left, right));
        }

        return pq.poll();
    }

    /**For each character in the tree, the Huffman codes are constructed recursively via the buildCodes method. To store the
     Huffman code for each character, it requires the root node, a string code, and a MapCharacter, String>. If the current node
     represents a character, the code is added to the code map if the node is not null. Then, it executes buildCodes recursively
     on the left and right child nodes, appending "0" to the left child's code and "1" to the right child's code.
      */
    public static void buildCodes(HuffmanNode node, String code, Map<Character, String> codeMap) {
        if (node == null) {
            return;
        }

        if (node.character != '\0') {
            codeMap.put(node.character, code);
        }

        buildCodes(node.left, code + "0", codeMap);
        buildCodes(node.right, code + "1", codeMap);
    }

    /**The input for the encode method is a string along with a Map<Character, String> of Huffman codes for every character.
     * The Huffman code for each character in the input string is added to a fresh StringBuilder that is created after iterating
     * through the input string. Finally, a string representing the StringBuilder is returned.**/
    public static String encode(String input, Map<Character, String> codeMap) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(codeMap.get(input.charAt(i)));
        }
        return sb.toString();
    }

    /** The root node of the Huffman tree and a string input are both inputs for the decode technique. In order to iterate through
     * the input string and navigate the Huffman tree depending on each character, a new StringBuilder is created. The character
     * advances to the left child if it is "0," and to the right kid if it is "1." When it comes to a leaf node, it resets the
     * current node to the root and adds the matching character to the StringBuilder. The StringBuilder is then returned as a string.**/
    public static String decode(String input, HuffmanNode root) {
        StringBuilder sb = new StringBuilder();
        HuffmanNode current = root;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current.character != '\0') {
                sb.append(current.character);
                current = root;
            }
        }
        return sb.toString();
    }
    /**Main method for testing. In the main method, the program creates a sample input string, builds the Huffman tree and codes, encodes the input string,
     * decodes the encoded string, and prints the original input string, the encoded string, and the decoded string for verification.**/
    public static void main(String[] args) {
        String input = "Hello,Sarina!";
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        HuffmanNode root = buildTree(frequencyMap);

        Map<Character, String> codeMap = new HashMap<>();
        buildCodes(root, "", codeMap);

        String encoded = encode(input, codeMap);
        String decoded = decode(encoded, root);

        System.out.println("Input: " + input);
        System.out.println("Encoded: " + encoded);
        System.out.println("Decoded: " + decoded);
    }
}
