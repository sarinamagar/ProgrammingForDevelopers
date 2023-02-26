package Assignment;
/**Given an array of even numbers sorted in ascending order and an integer k, Find the k^th missing even number from provided array
 Input a[] ={0, 2, 6, 18, 22} k=6
 Output: 16 examples:
 Explanation: Missing even numbers on the list are 4, 8, 10, 12, 14, 16, 20 and so on and kth missing number is on 6th place
 of the list i.e. 16**/
public class Question_8B {
    // Method to find the kth missing even number

    /**The method findKthMissingEvenNumber takes two arguments: an integer array a and an integer k, and returns an integer.**/
    public static int findKthMissingEvenNumber(int[] a, int k) {
        // Check for empty array or invalid k value
        if (a.length == 0 || k < 1 || k > a[a.length - 1] / 2) {
            return -1;
        }

        // Initializing two variables
        int currentMissingNumber = a[0]; // Considering the first missing even number
        int countMissing = 0; // Count of missing even numbers discovered

        /**The input array's items are iterated over in a loop beginning with the second element. A while loop is utilized inside
         * the loop to look for any missing even numbers between the current element and the following element. If a missing even
         * number is discovered, countMissing is increased and currentMissingNumber is changed to the following missing even number.
         * The current missing even number is returned if countMissing equals k.**/

        // Loop through the elements of the input array
        for (int i = 1; i < a.length; i++) {
            // Using a while loop, check if there are any missing even numbers between the current and next elements.
            while (currentMissingNumber + 2 < a[i]) {
                currentMissingNumber += 2; // update current missing number
                countMissing++; // increment number of missing even numbers found
                if (countMissing == k) { // return current missing number if it's the kth missing even number
                    return currentMissingNumber;
                }
            }
            currentMissingNumber = a[i]; // update current missing number to the current array element
        }

        /**Following the input array loop, a second while loop is used to look for any remaining missing even numbers up until the
         * kth missing even number is located or all of the missing even numbers have been found. The kth missing even number is given
         * back if it is discovered. If there aren't enough missing even numbers, a result of -1 is returned.**/

        // To find remaining missing even numbers after the end of the input array
        while (countMissing < k) {
            currentMissingNumber += 2; // update current missing number
            countMissing++; // increment number of missing even numbers found
            if (countMissing == k) { // return current missing number if it's the kth missing even number
                return currentMissingNumber;
            }
        }
        // Return -1 if there are not enough missing even numbers
        return -1;
    }

    // Main method to print the kth missing even number
    public static void main(String[] args) {
        int[] a = {0, 2, 6, 18, 22};
        int k = 6;
        int result = findKthMissingEvenNumber(a, k);
        if (result == -1) {
            System.out.println("Invalid input.");
        } else {
            System.out.println("The " + k + "th missing even number is: " + result);
        }
    }
}