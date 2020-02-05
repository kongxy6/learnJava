package baseAlgorithm.algorithm;

import org.junit.jupiter.api.Test;

class RemoveKdigits {
    public String removeKdigits(String num, int k) {
        if (k == 0) {
            return num;
        }
        while (true) {
            if (num.length() == 0) {
                return "0";
            }
            // 找到最长的升序区间
            int i = 0;
            int a = num.charAt(i) - '0';
            if (i + 1 == num.length()) {
                return "0";
            }
            int b = num.charAt(++i) - '0';
            while (a < b) {
                a = b;
                if (i + 1 == num.length()) {
                    ++i;
                    break;
                } else {
                    b = num.charAt(++i) - '0';
                }
            }
            // 移除a, i-1;
            --i;
            num = num.substring(0, i) + num.substring(i + 1, num.length());
            System.out.println(num);
            // 移除开头的0
            while (num.startsWith("0")) {
                num = num.substring(1, num.length());
            }
            --k;
            if (k == 0) {
                return num;
            }
        }
    }

    @Test
    public void test() {
        removeKdigits("1432219", 3);
    }

}
