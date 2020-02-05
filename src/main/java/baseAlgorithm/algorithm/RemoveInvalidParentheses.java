package baseAlgorithm.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RemoveInvalidParentheses {

    int remMax = Integer.MAX_VALUE;

    String origin;

    Set<String> set = new HashSet<>();

    public List<String> removeInvalidParentheses(String s) {
        this.origin = s;
        dfs(0, 0, 0, 0, "");
        return new ArrayList<>(set);
    }

    void dfs(int curr, int leftCount, int rightCount, int remCount, String s) {
        if (curr == origin.length()) {
            if (leftCount == rightCount) {
                if (remCount <= remMax) {
                    if (remCount < remMax) {
                        set.clear();
                        remMax = remCount;
                    }
                    set.add(s);
                }
            }
            return;
        }
        char a = origin.charAt(curr);
        if (a == '(' || a == ')') {
            dfs(curr + 1, leftCount, rightCount, remCount + 1, s);
            if (a == ')' && rightCount + 1 > leftCount) {
                return;
            }
            if (a == '(') {
                dfs(curr + 1, ++leftCount, rightCount, remCount, s + a);
            } else {
                dfs(curr + 1, leftCount, ++rightCount, remCount, s + a);
            }
        } else {
            dfs(curr + 1, leftCount, rightCount, remCount, s + a);
        }
    }
}