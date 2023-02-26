package Assignment;
/**You are given an even length array; divide it in half and return possible minimum product difference of any two subarrays.
 Note that the minimal product difference is the smallest absolute difference between any two arrays a and b, which is computed by calculating the difference after multiplying each element of the arrays a and b.
 Input: {5,2,4,11}
 Output: 2
 {5,4} {2,11} result into minimum product difference.**/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question_3A {
    /**This method takes an input array arr and returns a list of all possible subarrays of length 2. It achieves this by using
     * two nested loops to iterate through all pairs of distinct indices i and j, and then creating a new subarray with the elements
     * at those indices.**/
    public static List<int[]> findSubarrays(int[] arr) {
            List<int[]> subarrays = new ArrayList<>();
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    subarrays.add(new int[] {arr[i], arr[j]});
                }
            }
            return subarrays;
    }
    /**This method takes an input array arr, uses the findSubarrays method to generate all possible subarrays, calculates the product
     *  of each subarray, and stores the products in an array. It then sorts the array of products in ascending order, calculates the
     *  minimum difference between adjacent products, and returns this value as the minimum product difference between any two subarrays.**/
    public static int minimumProductDifference(int[] arr){
        List<int[]> subarrays = findSubarrays(arr);
        int subArraySize = subarrays.size();
        int[] productArray = new int[subArraySize];
        int index = 0;
        for (int[] subArray : subarrays) {
            int product = 1;
            for (int element : subArray) {
                product *= element;
            }
            productArray[index] = product;
            index++;
        }
        Arrays.sort(productArray);
        System.out.println(Arrays.toString(productArray));
        int minDiff = productArray[1]-productArray[0];
        for (int i = 2 ; i < productArray.length ; i++) {
            minDiff = Math.min(minDiff, productArray[i]-productArray[i-1]);
        }
        return minDiff;
    }
    public static void main(String[] args) {
      int[] arr = new int[]{5,4,2,11};
        int minimumProductDifference = minimumProductDifference(arr);
        System.out.println(minimumProductDifference);
    }
}
