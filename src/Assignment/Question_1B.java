package Assignment;
/**Assume you were hired to create an application for an ISP, and there is n number of network devices, such as routers, that are
 * linked together to provides internet access to home user users. You are given a 2D array that represents network connections
 * between these network devices such that a[i]=[xi,yi] where xi is connected to yi device. Suppose there is a power outage on
 * a certain device provided as int n represents id of the device on which power failure occurred)), Write an algorithm to return
 * impacted network devices due to breakage of the link between network devices. These impacted device list assists you notify
 * linked consumers that there is a power outage and it will take some time to rectify an issue. Note that: node 0 will always
 * represent a source of internet or gateway to international network.
 *
 * Input: edges= {{0,1}, {0,2}, {1,3}, {1,6}, {2,4}, {4,6}, {4,5}, {5,7}}
 * Target Device (On which power Failure occurred): 4
 * Output (Impacted Device List) = {5,7}
 * Explanation: power failure on network device 4 will disconnect 5 and 7 from internet.**/
import java.util.HashSet;
import java.util.Set;

/**The code uses a depth-first search (DFS) traversal to identify disconnected subtrees within the tree, where disconnected subtree
 *  refers to a subtree that is not connected to any other node or subtree in the tree. The DFS algorithm starts at the root node of
 *  the tree and traverses its left and right subtrees recursively.

 If a disconnected subtree is found during the traversal, the algorithm marks all nodes within the subtree as visited, and the number
 of service centers visited is incremented. The algorithm continues traversing the tree until all disconnected subtrees have been visited.**/
public class Question_1B {

    /** the minimumNumberOfServiceCenters method, which takes an integer array tree as its input and returns the minimum number of service
     * centers required to cover the tree. The method initializes the number of centers visited to zero and creates a HashSet visited to
     * store the nodes visited during the traversal.**/
        public static int minimumNumberOfServiceCenters(int[] tree) {
            int numCenters = 0;
            Set<Integer> visited = new HashSet<>();//kstores visited nodes during traversal.

            /**The method then loops through the array tree, checking each node to see if it is disconnected and not yet visited.
             * If a disconnected node is found, the number of centers visited is incremented, and the DFS algorithm is called to
             * mark all nodes in the disconnected subtree as visited.**/
            // Traversing through the tree
            for (int i = 0; i < tree.length; i++) {
                if (tree[i] == 0 && !visited.contains(i)) {// Check if the node is disconnected and not yet visited.
                    numCenters++;// Increasing the number of centres visited
                    dfs(i, tree, visited);// traversal to mark all nodes in the disconnected subtree as visited.
                }
            }

            return numCenters;// Return the minimum number of service centers.
        }

        /**The DFS algorithm is implemented in the dfs method, which takes the current node, the tree array, and the visited set as input.
         *  The method marks the current node as visited and checks if the left and right subtrees exist and are disconnected and not yet
         *  visited. If a disconnected subtree is found, the DFS algorithm is recursively called to mark all nodes in the subtree as visited.**/
        private static void dfs(int node, int[] tree, Set<Integer> visited) { //Recursive DFS traversal to mark nodes in the disconnected subtree as visited.
            visited.add(node);// Mark the current node as visited.

            int left = 2 * node + 1;
            int right = 2 * node + 2;


            if (left < tree.length && tree[left] == 0 && !visited.contains(left)) {// Recursively traverse through the left subtree if it exists and is disconnected and not yet visited.
                dfs(left, tree, visited);
            }

            if (right < tree.length && tree[right] == 0 && !visited.contains(right)) {// Recursively traverse through the right subtree if it exists and is disconnected and not yet visited.
                dfs(right, tree, visited);
            }

            if (node > 0 && tree[(node - 1) / 2] == 0 && !visited.contains((node - 1) / 2)) {// Recursively traverse through the parent node if it exists and is disconnected and not yet visited.
                dfs((node - 1) / 2, tree, visited);
            }
        }

        public static void main(String[] args) {
            int[] tree = {0, 0, -1, 0, -1, 0, -1, -1, 0};// Input the tree given in question
            int numCenters = minimumNumberOfServiceCenters(tree);// Calculate the minimum number of service centers required for the given tree.
            System.out.println("Minimum number of service centers required: " + numCenters);
        }

    }

/**The time complexity of the code depends on the size of the input tree, denoted by n, which represents the number of nodes in the tree.
 * The minimumNumberOfServiceCenters method loops through the tree array once, which takes O(n) time.
 * The DFS algorithm implemented in the dfs method visits each node in the disconnected subtree only once,
 * which takes O(1) time per node. However, the total number of nodes in all disconnected subtrees is bounded
 * by the number of nodes in the tree, which is O(n). Therefore, the total time complexity of the DFS algorithm is O(n).
 *
 * Combining the time complexities of both methods, we get a total time complexity of O(n^2), which is dominated by the O(n^2)
 * time complexity of the DFS algorithm.In summary, the time complexity of the code is O(n^2), where n is the number of nodes in the input tree.**/