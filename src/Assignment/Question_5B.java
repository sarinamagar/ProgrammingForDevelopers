package Assignment;
/**Assume an electric vehicle must go from source city s to destination city d. You can locate many service centers
 *  along the journey that allow for the replacement of batteries; however, each service center provides batteries
 *  with a specific capacity. You are given a 2D array in which servicecenter[i]=[xi,yj] indicates that the ith service
 *  center is xi miles from the source city and offers yj miles after the automobile can travel after replacing batteries
 *  at specific service centers. Return the number of times the car's batteries need to be replaced before arriving at the destination.
 *  Input: serviceCenters = [{10,60},{20,30},{30,30},{60,40}], targetMiles= 100, startChargeCapacity = 10
 Output: 2
 Explanation: The car can go 10 miles on its initial capacity; after 10 miles, the car replaces batteries with a capacity of 60 miles;
 and after travelling 50 miles, at position 60 we change batteries with a capacity of 40 miles; and ultimately, we may arrive at our destination.**/
import java.util.Arrays;
import java.util.Comparator;

public class Question_5B {
    /**The issue presupposes that there are numerous service locations nearby, each of which has a specific area that may be serviced
     *  by a single battery replacement. The goal is to determine the fewest number of battery changes required to go from a starting
     *  point to a target location. The solution uses a greedy approach to solve the problem.**/
    public static int batteryReplacement(int[][] serviceCenters, int targetMiles, int startChargeCapacity) {
        Arrays.sort(serviceCenters, Comparator.comparingInt(a -> a[0]));
        int replacements = 0;
        int currentMiles = startChargeCapacity;
        int i = 0;
        /**The service centers are first arranged according to their distance from the starting location in ascending order. If the
         * intended location has been reached or it has been decided that it cannot be reached, a loop is then run. The algorithm
         * determines the maximum service center range that can be reached with the current battery capacity at each iteration of the
         * loop. The algorithm gives -1 to show that the destination cannot be reached if no service center can be reached with the
         * available battery capacity. If not, the battery is changed at the service center with the greatest possible range, and
         * the battery capacity is updated appropriately. Each repetition increases the number of battery replacements made by one.**/
        while (currentMiles < targetMiles) {
            int maxMiles = 0;
            while (i < serviceCenters.length && serviceCenters[i][0] <= currentMiles) {
                maxMiles = Math.max(maxMiles, serviceCenters[i][1]);
                i++;
            }
            if (maxMiles == 0) {
                return -1;
            }
            currentMiles = maxMiles + currentMiles;
            replacements++;
        }
        return replacements;
    }
    /**Main method for testing.**/
    public static void main(String[] args) {
        int[][] serviceCenters = {{10,60},{20,30},{30,30},{60,40}};
        int targetMiles = 100;
        int startChargeCapacity = 10;
        System.out.println("Number of batteries required are "+batteryReplacement(serviceCenters, targetMiles, startChargeCapacity)+" batteries");
    }
}
/**Due to the sorting operation, the solution has an O(nlogn) time complexity, where n is the number of service centers. Yet, because
 * only a fixed amount of extra space is required, the space complexity is O(1).**/

