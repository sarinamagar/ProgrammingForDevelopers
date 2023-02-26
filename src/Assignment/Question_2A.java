package Assignment;
/**You are given a 2D array containing hierarchical information about certain species, with edge[i]=[xi,yi], where node xi
 * is connected to xj. You are also provided an array of values associated with each species, such that value[i] reflects
 * the ith nodes value. If the greatest common divisor of two values is 1, they are "relatively prime." Any other node on
 * the shortest path from that node to the absolute parent node is an ancestor of certain species i. Return a list of nearest
 * ancestors, where result[i] is the node i's nearest ancestor such that values[i] and value[result[i]] are both relative
 * primes otherwise -1.
 Input: values [3,2,6,6,4,7,12], edges= {{0,1}, {0,2}, {1,3}, {1,4}, {2,5}, {2,6}} Output: {-1,0, -1, -1,0,2, -1}**/

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Question_2A {
    static class Solution {
        public int gcd(int n1, int n2) {
            if (n2 == 0) {
                return n1;
            }
            return gcd(n2, n1 % n2);
        }

        /**The dfs method starts by checking whether the current node has already been visited. If it has, then the method
         *  returns. Otherwise, it marks the current node as visited and finds the closest ancestor with a coprime value by
         *  iterating over all possible coprime values in the poss array and checking if they exist in the map. If a coprime
         *  value is found, the method updates the ancestor and d variables if the depth difference to the coprime value is
         *  smaller than the previous closest ancestor. After finding the closest ancestor, the method stores it in the ans
         *  array for the current node. Then, the method recursively calls itself for each child node of the current node in
         *  the tree array. After the recursive calls are complete, the method removes the current node's value from the map
         *  if it exists, or updates the map to contain the previous occurrence of the value. Finally, the method returns.**/
        public void dfs(int[] nums, LinkedList<Integer>[] tree, int depth, int node, boolean[] visited, int[] ans, Map<Integer,int[]> map, boolean[][] poss) {
            if(visited[node]) return;
            visited[node] = true;
            int ancestor = -1;
            int d = Integer.MAX_VALUE;
            for(int i = 1; i < 51; i++) {
                if(poss[nums[node]][i] && map.containsKey(i)) {
                    if(depth - map.get(i)[0] <= d) {
                        d = depth - map.get(i)[0];
                        ancestor = map.get(i)[1];
                    }
                }
            }
            ans[node] = ancestor;
            int[] exist = (map.containsKey(nums[node])) ? map.get(nums[node]) :  new int[]{-1,-1};
            map.put(nums[node],new int[]{depth,node});
            for(int child : tree[node]) {
                if(visited[child]) continue;
                dfs(nums, tree, depth+1, child, visited, ans, map, poss);
            }
            if(exist[0] != -1) map.put(nums[node], exist);
            else map.remove(nums[node]);

            return;
        }
        /**The getCoprimes method is a public method that takes in an array of integers nums and a 2D array of integers edges
         *  representing the edges of the graph. The method creates a 2D boolean array poss that represents whether two numbers
         *  are coprime by iterating over all possible pairs of numbers up to 50 and calculating their gcd. It then creates a
         *  tree array of linked lists that represents the edges of the graph, initializes the ans array with -1 values, and
         *  creates a hashmap map that maps each value in the nums array to its most recent occurrence in the graph. The
         *  method then calls the dfs method with the appropriate parameters and returns the ans array.**/

        public int[] getCoprimes(int[] nums, int[][] edges) {
            boolean[][] poss = new boolean[51][51];
            for(int i = 1; i < 51; i++) {
                for(int j = 1; j < 51; j++) {
                    if(gcd(i,j) == 1) {
                        poss[i][j] = true;
                        poss[j][i] = true;
                    }
                }
            }
            int n = nums.length;
            LinkedList<Integer>[] tree = new LinkedList[n];

            for(int i =0 ; i < tree.length; i++) tree[i] = new LinkedList<>();
            for(int edge[] : edges) {
                tree[edge[0]].add(edge[1]);
                tree[edge[1]].add(edge[0]);
            }

            int[] ans = new int[n];
            Arrays.fill(ans, -1);
            ans[0] = -1;
            Map<Integer,int[]> map = new HashMap<>();

            boolean[] visited = new boolean[n];
            dfs(nums, tree, 0, 0, visited, ans, map, poss);
            return ans;
        }
    }

    public static void main(String[] args) {
        Question2A q2a = new Question2A();
        Solution sol = new Solution();
        int[] nums = {3,2,6,6,4,7,12};
        int[][] edges = {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6}};
        int[] result = sol.getCoprimes(nums, edges);
        System.out.println(Arrays.toString(result));
    }
}