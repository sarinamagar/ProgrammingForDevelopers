package Assignment;
/**Given a linked list containing an integer value, return the number of steps required to sort a linked list in
 ascending order by eliminating elements at each step Note: at each step remove element a[i] where a[i-1]> a[i]**/

/**This implementation has a time complexity of O(n log n), where n is the number of nodes in the linked list,
 * and a space complexity of O(log n) due to the recursive calls.**/
    public class Question_4B {

        /**The Node class represents a node in the linked list by providing a reference to the following node in the list
         * and an integer value (val) (next).**/
        static class Node {
            int val;
            Node next;

            Node(int val) {
                this.val = val;
            }
        }
        /**The sort method takes as input the head of the linked list and recursively divides the list into two halves using
         * the slow and fast pointer approach, and then sorts each half recursively. The merge operation is then performed to
         * merge the sorted halves back together, while keeping track of the number of steps it takes to merge the two halves.**/
        public static int sort(Node head) {
            if (head == null || head.next == null) {
                return 0;
            }

            // Divide the linked list into two halves
            Node slow = head;
            Node fast = head.next;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            Node mid = slow.next;
            slow.next = null;

            // Recursively sort the two halves
            int steps = sort(head) + sort(mid);

            // Merge the sorted halves
            Node dummy = new Node(0);
            Node curr = dummy;
            while (head != null && mid != null) {
                if (head.val < mid.val) {
                    curr.next = head;
                    head = head.next;
                } else {
                    curr.next = mid;
                    mid = mid.next;
                    steps++;
                }
                curr = curr.next;
            }
            curr.next = (head != null) ? head : mid;

            return steps;
        }

        /**The main method creates a linked list with four nodes and values 4, 3, 2, and 1, respectively. It then calls the sort method
         * on the head of the list and prints the number of steps required to sort the list. The sort method returns the number of steps
         * required to merge the sorted halves, which is the total number of inversions in the list. An inversion is defined as a pair
         * of elements in the list such that the left element is greater than the right element but appears before it in the list.
         * The number of inversions is a measure of how "out of order" the list is, and is useful for sorting algorithms like merge sort
         * that use a divide-and-conquer approach.**/
        public static void main(String[] args) {
            Node head = new Node(4);
            head.next = new Node(3);
            head.next.next = new Node(2);
            head.next.next.next = new Node(1);

            System.out.println("Steps required to sort: " + sort(head));

    }
}
