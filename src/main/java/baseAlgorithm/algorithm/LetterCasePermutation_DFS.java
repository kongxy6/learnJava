package baseAlgorithm.algorithm;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LetterCasePermutation_DFS {
    private final List<String> res = new ArrayList<>();

    private int len = 0;

    public void dfs(int num, String str, String S) {
        if (num == len) {
            res.add(str);
            return;
        }
        if (S.charAt(num) >= 'a' && S.charAt(num) <= 'z') {
            str += S.charAt(num);
            dfs(num + 1, str, S);
            str = str.substring(0, str.length() - 1);
            char tmp = (char) (S.charAt(num) - 'a' + 'A');
            str += tmp;
            dfs(++num, str, S);
        } else if (S.charAt(num) >= 'A' && S.charAt(num) <= 'Z') {
            str += S.charAt(num);
            dfs(num + 1, str, S);
            str = str.substring(0, str.length() - 1);
            str += (char) (S.charAt(num) - ('A' - 'a'));
            dfs(++num, str, S);
        } else {
            str += S.charAt(num);
            dfs(++num, str, S);
        }
    }

    @Test
    public void letterCasePermutation() {
        String S = "a1b2";
        len = S.length();
        dfs(0, "", S);
        System.out.println(res);
    }
}
