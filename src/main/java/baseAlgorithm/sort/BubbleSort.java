package baseAlgorithm.sort;

import org.junit.jupiter.api.Test;

public class BubbleSort {

    public static void sort(int[] array) {
        int len = array.length;
        for (int j = len; j > 0; --j) {
            boolean swap = false;
            for (int i = 0; i < j - 1; ++i) {
                if (array[i] > array[i + 1]) {
                    swap = true;
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
            if (!swap) {
                break;
            }
        }
    }

    @Test
    void test() {
        int[] a = {432, 324, 32, 4, 24, 324, 32, 4, 24, 23, 24, 23, 42, 3};
        BubbleSort.sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

}
