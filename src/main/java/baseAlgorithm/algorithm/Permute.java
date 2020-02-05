package baseAlgorithm.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Permute {
    int len = 0;
    List<List<Integer>> res = new ArrayList<>();

    public void dfs(List<Integer> l, int[] nums) {
        if (l.size() == len) {
            res.add(l);
            return;
        }
        for (int i = l.size(); i < len; ++i) {
            List<Integer> ll = new ArrayList<>();
            for (int j = 0; j < l.size(); ++j) {
                ll.add(l.get(j));
            }
            int[] tmp = new int[len];
            for (int j = 0; j < len; ++j) {
                tmp[j] = nums[j];
            }
            int t = tmp[i];
            tmp[i] = tmp[l.size()];
            tmp[l.size()] = t;
            ll.add(t);
            dfs(ll, tmp);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        this.len = nums.length;
        List<Integer> list = new ArrayList<>();
        dfs(list, nums);
        return res;
    }
}
