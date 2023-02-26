package Assignment;
/**There are n nations linked by train routes. You are given a 2D array indicating routes between countries and the time required to
 * reach the target country, such that E[i]=[xi,yi,ki], where xi represents the source country, yi represents the destination country,
 * and ki represents the time required to go from xi to yi. If you are also given information on the charges, you must pay while entering
 * any country. Create an algorithm that returns the cheapest route from county A to county B with a time constraint.
 *
 * Input: edge= {{0,1,5}, {0,3,2}, {1,2,5}, {3,4,5}, {4,5,6}, {2,5,5}} Charges = {10,2,3,25,25,4}
 * Source: 0
 * Destination: 5
 * Output: 64
 * Time Constraint=14 min
 * Note: the path 0, 3, 4, 5 will take minimum time i.e., 13 minutes and which costs around $64.
 * We cannot take path 0,1,2,5 as it takes 15 min and violates time constraint which in 14 min.
 **/
import java.util.*;

public class Question_1A {

    static class CheapestRouteWithTimeConstraint {

        public static int findCheapestRoute(int[][] edges, int[] charges, int source, int destination, int timeConstraint) {
            // Create a graph represented as an adjacency list
            Map<Integer, List<int[]>> graph = new HashMap<>();
            for (int[] edge : edges) {
                int from = edge[0];
                int to = edge[1];
                int time = edge[2];
                int cost = charges[to];
                List<int[]> list = graph.getOrDefault(from, new ArrayList<>());
                list.add(new int[]{to, time, cost});
                graph.put(from, list);
            }

            // Initialize the distances and visited flags
            int[] distances = new int[charges.length];
            Arrays.fill(distances, Integer.MAX_VALUE);
            distances[source] = 0;

            // Use a priority queue to select the node with the smallest distance
            PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
            queue.offer(new int[]{source, 0});

            // Dijkstra's algorithm with a time constraint
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int id = curr[0];
                int time = curr[1];
                if (id == destination) {
                    return distances[id] + charges[source];
                }
                if (time > timeConstraint) {
                    continue;
                }
                for (int[] neighbor : graph.getOrDefault(id, new ArrayList<>())) {
                    int newTime = time + neighbor[1];
                    int newCost = distances[id] + neighbor[2];
                    if (newTime <= timeConstraint && newCost < distances[neighbor[0]]) {
                        distances[neighbor[0]] = newCost;
                        queue.offer(new int[]{neighbor[0], newTime});
                    }
                }
            }

            return -1; // No path found
        }


        public static void main(String[] args) {
            int[][] edges = {{0, 1, 5}, {0, 3, 2}, {1, 2, 5}, {3, 4, 5}, {4, 5, 6}, {2, 5, 5}};
            int[] charges = {10, 2, 3, 25, 25, 4};
            int source = 0;
            int destination = 5;
            int timeConstraint = 14;
            int cheapestCost = CheapestRouteWithTimeConstraint.findCheapestRoute(edges, charges, source, destination, timeConstraint);
            System.out.println("Cheapest cost from " + source + " to " + destination + " within " + timeConstraint + " time units: " + cheapestCost);
        }
    }
}