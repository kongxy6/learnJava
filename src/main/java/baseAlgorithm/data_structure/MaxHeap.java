package baseAlgorithm.data_structure;

public class MaxHeap {

    public int[] pq;

    private int size = 0;

    private int capacity = 0;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.pq = new int[capacity + 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(int e) {
        if (size + 1 > capacity) {
            return;
        }
        pq[++size] = e;
        swim(size);
    }

    public int delete() {
        if (size == 0) {
            return 0;
        }
        int max = pq[1];              //下标1的节点是最大值
        pq[1] = pq[size];
        --size;
        sink(1);                 //恢复删除以后堆的有序
        return max;
    }

    // 上浮操作，将大的浮到最顶层
    private void swim(int k) {
        while (k > 1 && pq[k] > pq[k / 2]) {
            int n = pq[k];
            pq[k] = pq[k / 2];
            pq[k / 2] = n;
            k = k / 2;
        }
    }

    // 下沉操作，将新的首节点下沉到合适的位置
    private void sink(int k) {
        while (2 * k <= size) {
            int j = k * 2;
            int n = pq[j];
            if (2 * k + 1 <= size) {
                if (pq[k * 2 + 1] > n) {
                    ++j;
                    n = pq[j];
                }
            }
            if (n > pq[k]) {
                pq[j] = pq[k];
                pq[k] = n;
                k = j;
            } else {
                break;
            }
        }
    }
}
