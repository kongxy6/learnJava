package baseAlgorithm.data_structure;

import org.junit.jupiter.api.Test;

public class ReverseLinkedList {

    class Node {

        public Node(Node next, int val) {
            this.next = next;
            this.val = val;
        }

        public Node next;
        public int val;
    }

    @Test
    public void main() {
        Node head = new Node(null, 0);
        Node p = head;
        for (int i = 1; i < 8; i++) {
            p.next = new Node(null, i);
            p = p.next;
        }
        Node tail = head.next;
        p = head.next.next;
        while (p != null) {
            tail.next = p.next;
            p.next = head.next;
            head.next = p;
            p = tail.next;
        }
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

}
