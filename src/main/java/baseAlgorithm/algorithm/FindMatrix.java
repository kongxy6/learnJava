package baseAlgorithm.algorithm;

import org.junit.jupiter.api.Test;

class FindMatrix {

    int[][] matrix = new int[][]{{-10, -10, -8, -7, -6}, {-4, -4, -3, -2, 0}, {1, 1, 2, 2, 4}};

    @Test
    public void test() {
        searchMatrix();
    }

    public boolean searchMatrix() {
        int target = -4;
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int col = matrix[0].length - 1;
        for (int i = 0; i < matrix.length; ++i) {
            if (target >= matrix[i][0] && target <= matrix[i][col]) {
                int l = 0, r = col;
                while (true) {
                    if (target >= matrix[i][(l + col) / 2]) {
                        l = (l + r) / 2;
                    } else {
                        r = (l + r) / 2;
                    }
                    if (l + 1 == r || l == r) {
                        if (target == matrix[i][l] || target == matrix[i][r]) {
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
