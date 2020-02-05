package baseAlgorithm.algorithm;

public class LongestPalindrome {
    public static String longestPalindrome(String s) {
        // dp[i][j] 表示是否是回文
        // dp[i][j] = dp[i+1][j-1] && s[i] == s[j]
        boolean[][] dp = new boolean[s.length()][s.length()];
        int b = 0, e = 0, max = 1;
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }
        for (int j = 1; j < s.length(); j++) {
            for (int i = j - 1; i >= 0; i--) {
                if (i + 1 == j) {
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = true;
                        if (j - i + 1 > max) {
                            b = i;
                            e = j;
                            max = 2;
                        }
                    } else {
                        dp[i][j] = false;
                    }
                } else {
                    if (dp[i + 1][j - 1]) {
                        if (s.charAt(i) == s.charAt(j)) {
                            dp[i][j] = true;
                            if (j - i + 1 > max) {
                                b = i;
                                e = j;
                                max = j - i + 1;
                            }
                        }
                    } else {
                        dp[i][j] = false;
                    }
                }
            }
        }
        return s.substring(b, e + 1);
    }
}
