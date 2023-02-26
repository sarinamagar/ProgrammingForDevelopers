import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**You are given an array of different words and target words. Each character of a word represents a different digit ranging from
 * 0 to 9, and no two character are linked to same digit. If the sum of the numbers represented by each word on the array equals
 * the sum the number represented by the targeted word, return true; otherwise, return false. Note: Provided list of words and
 * targeted word is in the form of equation
 Input: words = ["SIX","SEVEN","SEVEN"], result = "TWENTY" Output: true
 Explanation:
 s=6
 I=5
 X=0,
 E=8,
 V=7,
 N=2,
 T=1,
 W=3,
 Y=4
 SIX +SEVEN + SEVEN = TWENTY 650 + 68782 + 68782 = 138214**/
public class Question_6B {

    public static class WordSum {
        private static final int[] POW_10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000};
        /**
         * The isSolvable method first initializes a set charSet to keep track of all the unique characters in the words and result string,
         * an array charcount to keep track of the sum of each character based on where it appears in the words and result string, and a
         * boolean array nonLeadingZero to indicate whether each character can be given a value of 0. To calculate charCount and set the
         * nonLeadingZero flag for each character, a loop is used through the words and result string.
         **/
        public boolean isSolvable(String[] words, String result) {// Defining a boolean method named isSolvable that takes in an array of strings named words and a string named result
            Set<Character> charSet = new HashSet<>();// Creating a set of characters named charSet
            int[] charCount = new int[91];// Creating an integer array named charCount of size 91
            boolean[] nonLeadingZero = new boolean[91];// Creating a boolean array named nonLeadingZero of size 91
            for (String word : words) {// Iterating over the words in the words array
                char[] cs = word.toCharArray();// Converting the current word to a character array named cs
                for (int i = 0; i < cs.length; i++) {// Iterating over the characters in the current word
                    if (i == 0 && cs.length > 1) nonLeadingZero[cs[i]] = true;// If the current character is the first character of the current word and the length of the current word is greater than 1, setting the value of nonLeadingZero at the index of the current character to true
                    charSet.add(cs[i]);// Adding the current character to the charSet
                    charCount[cs[i]] += POW_10[cs.length - i - 1];// Incrementing the value of charCount at the index of the current character by POW_10[cs.length - i - 1]
                }
            }
            char[] cs = result.toCharArray();// Converting the result string to a character array named cs
            for (int i = 0; i < cs.length; i++) {// Iterating over the characters in the result string
                if (i == 0 && cs.length > 1) nonLeadingZero[cs[i]] = true;// If the current character is the first character of the result string and the length of the result string is greater than 1, setting the value of nonLeadingZero at the index of the current character to true
                charSet.add(cs[i]);// Adding the current character to the charSet
                charCount[cs[i]] -= POW_10[cs.length - i - 1];// Decrementing the value of charCount at the index of the current character by POW_10[cs.length - i - 1]
            }
            /**The process then creates a list of distinct characters called charList, an array to keep track of the values that have been
             * utilized, and an array values to hold the assigned value for each character. Recursively calling the isSolvableHelper function
             * verifies each potential value assignment to a character. If a value has not yet been assigned to the character in the current
             * index, it does so at each recursive iteration before determining if the puzzle may be solved. If not, it goes back and tries
             * the subsequent value.**/
            List<Character> charList = new ArrayList<>(charSet);
            boolean[] used = new boolean[10];// Creating a boolean array named used of size 10
            int[] values = new int[charList.size()];// Creating an integer array named values of size charList.size()
            return isSolvableHelper(charCount, nonLeadingZero, charList, used, values, 0);
        }
        /**
         * If all characters have been given values and the sum of the values for each character in charCount equals zero, the word sum puzzle
         * has been solved, the isSolvableHelper method returns true. In the main method, a WordSum class instance is created, a word sum
         * puzzle containing words and a result is defined, and the isSolvable method is called to solve the puzzle and print the answer.
         **/
        private boolean isSolvableHelper(int[] charCount, boolean[] nonLeadingZero, List<Character> charList, boolean[] used, int[] values, int idx) {
            if (idx == charList.size()) {// Check if all characters have been assigned a value
                int sum = 0;// Calculate the sum of the words based on the assigned values
                for (int i = 0; i < charCount.length; i++) {
                    if (charCount[i] != 0) {
                        sum += charCount[i] * values[charList.indexOf((char) i)];
                    }
                }
                return sum == 0;// If the sum is 0, the equation is solvable
            }
            for (int i = 0; i < 10; i++) {// Assign a value to the character at the current index
                if (!used[i] && (i != 0 || !nonLeadingZero[charList.get(idx)])) {// Check if the value has not been used yet and it is not a leading zero for a character
                    used[i] = true;
                    values[idx] = i;
                    if (isSolvableHelper(charCount, nonLeadingZero, charList, used, values, idx + 1)) {// Recursively call the function to assign values to the next character
                        return true;
                    }
                    used[i] = false;
                }
            }
            return false;// If none of the values result in a solvable equation, return false
        }

        public static void main(String[] args) {
            String[] words = new String[]{"SIX", "SEVEN", "SEVEN"};
            String result = "YTNEWT";
            WordSum wordSum = new WordSum();
            System.out.println(wordSum.isSolvable(words, result));
        }
    }
}