package baseAlgorithm.sort;

class ArrayQuickSort {
    public static void main(String[] args) {
        int[][] intervals = {{1, 2}};
        qSort(intervals, 0, intervals.length - 1);
        System.out.println(intervals);
    }

    static void qSort(int[][] intervals, int begin, int end) {
        if (end - begin < 1) {
            return;
        }
        int k = intervals[begin][0];
        int i = begin + 1;
        int j = end;
        while (i < j) {
            while (i < j) {
                if (intervals[j][0] < k) {
                    break;
                } else {
                    --j;
                }
            }

            while (i < j) {
                if (intervals[i][0] >= k) {
                    break;
                } else {
                    ++i;
                }
            }

            if (i < j) {
                int[] m = intervals[i];
                intervals[i] = intervals[j];
                intervals[j] = m;
            }
        }
        if (intervals[i][0] < k) {
            int[] m = intervals[i];
            intervals[i] = intervals[begin];
            intervals[begin] = m;
        }
        qSort(intervals, begin, i - 1);
        qSort(intervals, i + 1, end);
    }
}
