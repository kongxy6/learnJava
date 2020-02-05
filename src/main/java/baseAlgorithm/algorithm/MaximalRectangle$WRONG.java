package baseAlgorithm.algorithm;

import org.junit.jupiter.api.Test;

public class MaximalRectangle$WRONG {
    public int maximalRectangle(char[][] m) {
        if (m == null || m.length == 0) {
            return 0;
        }
        Obj[][] dict = new Obj[m.length][m[0].length];
        int mm = 0;
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m[0].length; ++j) {
                int max = 0;
                if (m[i][j] == '1') {
                    max = 1;
                    int x = i - 1, y = j - 1;
                    if (x >= 0 && y >= 0) {
                        if (m[x][j] == '1' && m[i][y] == '1') {
                            int m1 = (i - dict[x][j].x0 + 1) *
                                    (j - Math.max(dict[x][j].y0, dict[i][y].y0) + 1);
                            int m2 = (j - dict[i][y].y0 + 1) *
                                    (i - Math.max(dict[x][j].x0, dict[i][y].x0) + 1);
                            // 这里并不能选择出最适合下一步的方向
                            if (m1 > m2) {
                                max = m1;
                                dict[i][j] = new Obj(dict[x][j].x0,
                                        Math.max(dict[x][j].y0, dict[i][y].y0));
                            } else {
                                max = m2;
                                dict[i][j] = new Obj(
                                        Math.max(dict[x][j].x0, dict[i][y].x0), dict[i][y].y0);
                            }
                        } else if (m[x][j] == '1') {
                            max = Math.max(max, i - dict[x][j].x0 + 1);
                            dict[i][j] = new Obj(dict[x][j].x0, j);
                        } else if (m[i][y] == '1') {
                            max = Math.max(max, j - dict[i][y].y0 + 1);
                            dict[i][j] = new Obj(i, dict[i][y].y0);
                        } else {
                            max = 1;
                            dict[i][j] = new Obj(i, j);
                        }
                    } else if (y >= 0) {
                        if (m[i][y] == '1') {
                            max = Math.max(max, j - dict[i][y].y0 + 1);
                            dict[i][j] = new Obj(0, dict[i][y].y0);
                        } else {
                            max = 1;
                            dict[i][j] = new Obj(0, j);
                        }
                    } else if (x >= 0) {
                        if (m[x][j] == '1') {
                            max = Math.max(max, i - dict[x][j].x0 + 1);
                            dict[i][j] = new Obj(dict[x][j].x0, 0);
                        } else {
                            max = 1;
                            dict[i][j] = new Obj(i, 0);
                        }
                    } else {
                        max = 1;
                        dict[0][0] = new Obj(0, 0);
                    }
                    if (max > mm) {
                        mm = max;
                    }
                } else {

                }
            }
        }
        return mm;
    }

    @Test
    public void test() {
        char[][] m = {{'1', '0', '1', '1', '1', '0', '0', '0', '1', '0'},
                {'0', '1', '0', '0', '0', '0', '0', '1', '1', '0'},
                {'0', '1', '0', '1', '0', '0', '0', '0', '1', '1'},
                {'1', '1', '1', '0', '0', '0', '0', '0', '1', '0'},
                {'0', '1', '1', '1', '0', '0', '1', '0', '1', '0'},
                {'1', '1', '0', '1', '1', '0', '1', '1', '1', '0'}};
        maximalRectangle(m);
    }

    class Obj {
        public int x0;
        public int y0;

        public Obj(int x, int y) {
            x0 = x;
            y0 = y;
        }
    }
}
