package Assignment;

/** 5A
 * You are given a 2D array containing coordinates and height of rectangle such that height[i]=[xi,yi,hi], where xi
 * the x coordinate of left edge, yi represents x coordinate of right edge of rectangle and hi represents the height
 * of the peaks of each rectangle. If you want to construct a border line over the peaks of rectangle represented in
 * bar chart, return the key coordinates required to build a border line that contacts the peaks of the given chart.
 Note: key points are the left coordinates of shape representing peaks where you need to draw boarder line.**/

/**The approach is known as the "merge and split" algorithm, also referred to as the "divide and conquer" algorithm.
 * The principle behind this approach is to iteratively break the set of buildings into smaller subsets, identify the
 * skylines of each subset, and then combine the skylines to obtain the skyline of the complete set.**/

/**The merge and split algorithm has a time complexity of O(n log n), where n is the number of buildings.
 *  This is faster than the time complexity of the algorithm in the original code, which is O(n^2) in the worst case.
 *  However, the merge and split algorithm is more complex to implement and requires more memory.**/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question_5A {

    //The 2D array of integers height is the input argument for the getBorderLine method, which returns a 2D array of integers as the result.
    public static int[][] getBorderLine(int[][] height) {
        /**The input height is first checked to see if it is null or has length 0. An empty 2D array of integers,
         * new int[0][2,] is returned if either of these conditions is true.In order to handle the situation where
         * the input array is null or empty, this check is provided. There are no buildings to render if the input
         * array is empty or null,hence the output must also be empty in this case. As a result, the function produces
         * a 2D array of empty numbers to indicate that no buildings need to be shown.**/
        if (height == null || height.length == 0) { // Check if input is null or empty, return an empty 2D array in that case
            return new int[0][2];
        }
        /**The line determines if the input height only contains one element.If so, it produces a 2D array with two points:
         * the right endpoint of the rectangle and 0, as there isn't another rectangle to merge with, and the left terminus
         * of the rectangle and the height of the rectangle.**/
        if (height.length == 1) {// If there's only one building, return the border line of that building
            return new int[][]{{height[0][0], height[0][2]}, {height[0][1], 0}};
        }
        /**The next block of code gets executed if the input height has more than one element.
         * The first line calculates the midpoint index of height and stores it in mid.
         * The second and third lines create two subarrays left and right of height, by using the copyOfRange method of
         * the Arrays class. The left subarray contains elements from the beginning of height up to mid, and the right
         * subarray contains elements from mid to the end of height.
         * The fourth and fifth lines recursively call the getBorderLine method on left and right, and store the resulting
         * skyline of each subarray in leftSkyline and rightSkyline.
         * The final line merges the left and right skylines by calling the merge method with leftSkyline and rightSkyline as arguments.**/
        int mid = height.length / 2;// Divide the array into two halves and recursively get the border line for each half
        int[][] left = Arrays.copyOfRange(height, 0, mid);
        int[][] right = Arrays.copyOfRange(height, mid, height.length);
        int[][] leftSkyline = getBorderLine(left);
        int[][] rightSkyline = getBorderLine(right);

        return merge(leftSkyline, rightSkyline);// Merge the two border lines
    }

    private static int[][] merge(int[][] left, int[][] right) {
        /**The function begins by initializing variables to maintain track of the current indices of the left and right arrays being
         * compared as well as the lengths of the two input arrays. Additionally, it initializes variables to record the highest building
         * in each of the left and right arrays, as well as the highest building at the current index.**/
        int n = left.length;
        int m = right.length;
        int i = 0, j = 0;
        int h1 = 0, h2 = 0, maxH = 0;
        /**After initializing an empty ArrayList to house the combined arrays, the function begins a loop that keeps running
         *  until either the left or right array has been processed in full.The function compares the starting locations of the
         *  existing structures in the left and right arrays during the loop. The function adds the merged building to the ArrayList
         *  if the ArrayList is empty or the maximum height is different from the height of the last building in the ArrayList and
         *  sets the height of the current building in the left array to h1 if the start point of the current building in the left array
         *  is less than the start point of the current building in the right array. The left array's index is subsequently increased using the function.**/
        List<int[]> merged = new ArrayList<>();
        while (i < n && j < m) {
            // If the left building starts before the right building
            if (left[i][0] < right[j][0]) {
                // Set the height of the left building to h1
                h1 = left[i][1];
                // The maximum height so far is the maximum of h1 and h2
                maxH = Math.max(h1, h2);
                // Add the current point to the merged list if it's the first point or if the height has changed
                if (merged.size() == 0 || maxH != merged.get(merged.size() - 1)[1]) {
                    merged.add(new int[]{left[i][0], maxH});
                }
                // Move to the next building in the left array
                i++;
            }
            /**The function sets the height of the current building in the right array to h2, sets the maximum height of the merged
             * building to the maximum of h1 and h2, and adds the merged building to the ArrayList if the ArrayList is empty or the
             * maximum height is different from the height of the last building in the ArrayList if the start point of the current
             * building in the left array is greater than the start point of the current building in the right array.
             * The right array's index is then increased by the function.**/
            // If the right building starts before the left building
            else if (left[i][0] > right[j][0]) {
                // Set the height of the right building to h2
                h2 = right[j][1];
                // The maximum height so far is the maximum of h1 and h2
                maxH = Math.max(h1, h2);
                // Add the current point to the merged list if it's the first point or if the height has changed
                if (merged.size() == 0 || maxH != merged.get(merged.size() - 1)[1]) {
                    merged.add(new int[]{right[j][0], maxH});
                }
                // Move to the next building in the right array
                j++;
            }
            // If the left and right buildings start at the same point
            /**The function sets the height of the current building in the left array to h1, the height of the current building in
             * the right array to h2, sets the maximum height of the merged building to the maximum of h1 and h2, and adds the merged
             * building to the ArrayList if the ArrayList is empty or the maximum height is different from the height. If the start
             * points of the current building in the left array and the current building in the right array are equal, the function sets
             * the height of the current building The function then raises the indexes of the left array and the right array, respectively.**/
            else {// Set the height of the left and right buildings to h1 and h2, respectively
                h1 = left[i][1];
                h2 = right[j][1];
                maxH = Math.max(h1, h2);// Add the current point to the merged list if it's the first point or if the height has changed
                if (merged.size() == 0 || maxH != merged.get(merged.size() - 1)[1]) { //Check if the merged list is empty or if the maximum height of the current left and right buildings is different from the height of the last building in the merged list.
                    merged.add(new int[]{left[i][0], maxH});// adding building to merge
                }
                i++;//increment i
                j++;// move to the next building in the left array
            }
        }
        /**The function then determines whether there are any unprocessed buildings in the left array and, if so, adds them to the merged ArrayList
         * if their height differs from the building that was previously in the ArrayList. The function then determines whether there are any
         * unprocessed buildings left in the right array, and if so, adds them to the merged ArrayList if their height differs from the building
         * that was previously in the ArrayList.**/
        while (i < n) { //while left has not been completely traversed
            if (merged.size() == 0 || left[i][1] != merged.get(merged.size() - 1)[1]) {//add the remaining buildings in left to the merged list if their height is different from the height of the last building in merged.
                merged.add(left[i]);
            }
            i++;//move to the next builidign in array
        }
        while (j < m) {//while right has not been completely traversed
            if (merged.size() == 0 || right[j][1] != merged.get(merged.size() - 1)[1]) {//add the remaining buildings in right to the merged list if their height is different from the height of the last building in merged.
                merged.add(right[j]);
            }
            j++; //move to the next builidign in array
        }
        return toArray(merged);
    }
       /**Finally, the function converts the merged ArrayList to a two-dimensional integer array using the toArray method,
        *  and returns the sorted array of merged building arrays.**/
       private static int[][] toArray(List<int[]> list) {  //convert a list of integer arrays to a 2D integer array.
           int[][] arr = new int[list.size()][2];//create a new 2D integer array with the same dimensions.
           for (int i = 0; i < list.size(); i++) {// loop through the list and copy the value from each integer array into the new 2D array
               arr[i][0] = list.get(i)[0];
               arr[i][1] = list.get(i)[1];
           }
           return arr;
       }

//Main method for testing.
    public static void main(String[] args) {
        int[][] height = {{1, 4, 10}, {2, 5, 15}, {5, 8, 12}, {9, 11, 1}, {11, 13, 15}};
        int[][] borderLine = getBorderLine(height);
        System.out.println(Arrays.deepToString(borderLine));
    }
}
/**The time complexity of the given code is O(n log n), where n is the number of buildings in the input height array.

 * The getBorderLine() method is recursively dividing the input array into two halves until there is only one building left.
 * This operation has a time complexity of O(log n).
 *
 * The merge() method, which is called on every pair of subarrays, has a time complexity of O(n) as it needs to iterate over
 * all the buildings in the two subarrays and add them to the merged list. Additionally, it performs constant-time operations
 * such as Math.max() and ArrayList operations.
 *
 * Finally, the toArray() method has a time complexity of O(n) as it needs to iterate over the merged list and convert it to a 2D array.
 *
 * Since merge() is called log n times, the total time complexity of the algorithm is O(n log n).**/
