package Assignment;
/**You are given an array of binary trees that represent different cities where a certain corporation has its branch office
 *  and the organization wishes to provide service by constructing a service center. Building service centers at any node,
 *  i.e., a city can give service to its directly connected cities where it can provide service to its parents, itself, and
 *  its immediate children. Returns the smallest number of service centers required by the corporation to provide service to
 *  all connected cities. Note that: the root node represents the head office and other connected nodes represent the branch
 *  office connected to the head office maintaining some kind of hierarchy.
 Input: tree= {0,0, null, 0, null, 0, null, null, 0}
 Output: 2
 Explanation: construction of two service centers denoted by black markk will be enough to provide service to all cities.**/
public class Question_2B {

    /**
     * The binary tree is represented using a class TreeNode that has an integer value val, and left and right child nodes.
     **/
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static class SmallestNoOfServiceCenter {

        /**
         * The helper method first checks if the input TreeNode is null, in which case it returns an array of two zeros.
         * Otherwise, it recursively calls the helper method on the left and right child nodes, and stores their return values in the left and right arrays.
         **/
        private int[] helper(TreeNode root) {
            if (root == null) {
                return new int[]{0, 0};
            }

            int[] left = helper(root.left);
            int[] right = helper(root.right);

            int centers = left[1] + right[1];
            /**Next, the function calculates the number of service centers required for the current node by adding the second elements of the left and right arrays.
             * If the input TreeNode has no grandchildren, then only one service center is required, and the function returns an array of {1,1}.**/
            if (left[0] == 0 && right[0] == 0) {
                return new int[]{1, 1};
            }
            /**Otherwise, the function returns an array of {2, centers + 1}, where centers is the number of service centers required for the left and right subtrees,
             * and 1 is added to account for the current TreeNode**/
            return new int[]{2, centers + 1};
        }

        /**
         * The minServiceCenters method is a wrapper function that calls the helper function on the root of the binary tree
         * and returns the first element of the resulting array, which represents the minimum number of service centers required
         * for the entire tree.
         **/
        public int minServiceCenters(TreeNode root) {
            int[] result = helper(root);
            return result[0];
        }

        public static void main(String[] args) {
            TreeNode root = new TreeNode(0);
            root.left = new TreeNode(0);
            root.right = new TreeNode(0);
            root.left.left = null;
            root.left.right = new TreeNode(0);
            root.right.left = null;
            root.right.right = null;
            root.left.right.left = new TreeNode(0);

            SmallestNoOfServiceCenter smallestNoOfServiceCenter = new SmallestNoOfServiceCenter();
            int minCenters = smallestNoOfServiceCenter.minServiceCenters(root);

            System.out.println("Minimum number of service centers required: " + minCenters);
        }
    }
}
/**The time complexity of the code is O(n), where n is the number of nodes in the binary tree. The helper function recursively
 *  visits each node in the binary tree exactly once, and performs a constant amount of work at each node. Therefore, the total
 *  time complexity is proportional to the number of nodes in the tree.**/