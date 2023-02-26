package Assignment;
/**you are provided certain string and pattern, return true if pattern entirely matches the string otherwise return false.
 * Note: if pattern contains char @ it matches entire sequence of characters and # matches any single character within string.
 Input: String a=“tt”, pattern =”@”
 Output: true
 Input: String a=“ta”, pattern =”t” Output: false
 Input: String a=“ta”, pattern =”t#” Output: true**/
public class Question_3B {
    public static void main(String[] args) {
        String strA = "tt";
        String pattern = "##";
        //The indexA and patternIndex variables are initialized to 0, which represent the current indices of strA and pattern, respectively
        int indexA = 0;
        int patternIndex = 0;

        /**When patternIndex is less than the length of the pattern string, the program enters a loop that continues to run.
         * A switch statement is employed within this loop to determine which character is currently present in the pattern string.**/
        while (patternIndex < pattern.length()) {
            switch (pattern.charAt(patternIndex)) {
                /**If the current character is @, patternIndex is incremented by 1, and indexA is set to the length of strA. This means
                 *  that any remaining characters in strA can match this part of the pattern.**/
                case '@':
                    patternIndex++;
                    indexA = strA.length();
                    break;
                /**f the current character is #, the program checks if indexA is less than the length of strA. If it is, indexA is
                 *  incremented by 1 and patternIndex is also incremented by 1. This means that the current character in strA matches
                 *  this part of the pattern.**/
                case '#':
                    if (indexA < strA.length()) {
                        indexA++;
                        patternIndex++;
                    }
                    /**If the current character is not @ or #, the program checks if indexA is less than the length of strA and if the
                     *  current character in strA matches the current character in pattern. If both conditions are true, indexA and
                     *  patternIndex are incremented by 1, meaning that this part of the pattern matches the corresponding characters in strA.**/
                    else {
                        System.out.println("false");
                        return;
                    }
                    break;
                    /**If any of the above conditions fail, the program prints "false" and returns. If the loop completes successfully,
                     *  the program checks if indexA is equal to the length of strA and patternIndex is equal to the length of pattern.
                     *  If both conditions are true, the program prints "true", meaning that strA matches the pattern. Otherwise, it prints "false".**/
                default:
                    if (indexA < strA.length() && strA.charAt(indexA) == pattern.charAt(patternIndex)) {
                        indexA++;
                        patternIndex++;
                    } else {
                        System.out.println("false");
                        return;
                    }
            }
        }

        if (indexA == strA.length() && patternIndex == pattern.length()) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
/**The time complexity of the provided code is O(n), where n is the length of the pattern string. This is because the code iterates
 * through each character in the pattern string once, and the operations performed for each character are constant time operations
 * (comparisons, increments, etc.). Therefore, the total time complexity of the code is proportional to the length of the pattern
 * string, and can be considered linear.**/