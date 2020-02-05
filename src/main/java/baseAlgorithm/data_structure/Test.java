package baseAlgorithm.data_structure;

public class Test {

    @org.junit.jupiter.api.Test
    void test1() {
        MaxHeap maxHeap = new MaxHeap(10);
        for (int i = 10; i > 0; --i) {
            maxHeap.insert(i);
        }
        for (int i = 0; i < 10; ++i) {
            System.out.println(maxHeap.pq[i + 1]);
        }
        for (int i = 10; i > 0; --i) {
            System.out.println(maxHeap.delete());
        }
    }

}
