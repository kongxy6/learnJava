package baseAlgorithm.sort;

import org.junit.jupiter.api.Test;

public class QuickSort {

    /* 记也得记住！艹*/
    public static void sort(int[] array, int begin, int end) {
        int first;
        if (end - begin < 1) {
        } else {
            // 以首元素为基准，左边的小于等于它，右边的大于它
            first = array[begin];
            int j = end;
            int i = begin;
            while (i < j) {
                while (j > i && array[j] > first) {
                    --j;
                }
                while (i < j && array[i] <= first) {
                    ++i;
                }
                int a = array[j];
                if (i < j) {
                    array[j] = array[i];
                    array[i] = a;
                }
            }
            // i == j 此时值是小于等于首元素的，需要交换
            if (array[j] < first) {
                array[begin] = array[j];
                array[j] = first;
            }
            sort(array, begin, i - 1);
            sort(array, i + 1, end);
        }
    }

    @Test
    void test() {
        int[] a = {15, 7};
        //int[] a = {1, 4, 1};
        sort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

}
