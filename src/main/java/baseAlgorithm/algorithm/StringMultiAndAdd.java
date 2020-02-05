package baseAlgorithm.algorithm;

public class StringMultiAndAdd {

    // 不可变对象，下层栈修改值不影响上层栈的值
    String stringAdd(String num1, String num2) {
        int length1 = num1.length();
        int length2 = num2.length();
        String res = "";
        boolean mark = false;
        if (length1 > length2) {
            for (int i = 0; i < length1 - length2; i++) {
                num2 = '0' + num2;
            }
        } else if (length1 < length2) {
            for (int i = 0; i < length2 - length1; i++) {
                num1 = '0' + num1;
            }
        }
        length1 = num1.length();
        for (int i = 0; i < length1; ++i) {
            int a = num1.charAt(length1 - i - 1) - '0';
            int b = num2.charAt(length1 - i - 1) - '0';
            int c = 0;
            if (mark) {
                c = a + b + 1;
            } else {
                c = a + b;
            }
            if (c >= 10) {
                res += (char) (c + 38);
                mark = true;
            } else {
                res += (char) (c + 48);
                mark = false;
            }
        }
        if (mark) {
            res += '1';
        }
        return new StringBuffer(res).reverse().toString();
    }

    String stringMulti(String num1, char m) {
        int a = m - '0';
        int length = num1.length();
        int mark = 0;
        String res = "";
        for (int i = 0; i < length; i++) {
            int b = num1.charAt(length - i - 1) - '0';
            int c = a * b;
            if (mark > 0) {
                c += mark;

            }
            if (c >= 10) {
                res += (char) (c % 10 + 48);
                mark = c / 10;
            } else {
                res += (char) (c + 48);
                mark = 0;
            }
        }
        if (mark > 0) {
            res += (char) (mark + 48);
        }
        return new StringBuffer(res).reverse().toString();
    }
}
