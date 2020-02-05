package baseAlgorithm.algorithm;

import baseAlgorithm.sort.QuickSort;

import java.util.ArrayList;
import java.util.List;

public class SumThree {

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) {
            return null;
        }
        List<List<Integer>> lists = new ArrayList<>();
        QuickSort.sort(nums, 0, nums.length - 1);
        for (int i = 0; i < nums.length; ++i) {
            if (i > 0 && nums[i] == nums[i - 1]) {              // skip same result
                continue;
            }
            int a = 0 - nums[i];
            int[] newData = nums;
            int b = i + 1, e = newData.length - 1;
            while (b < e) {
                if (newData[b] + newData[e] < a) {
                    ++b;
                } else if (newData[b] + newData[e] > a) {
                    --e;
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(newData[b]);
                    list.add(newData[e]);
                    lists.add(list);
                    ++b;
                    --e;
                    while (b < e && nums[b] == nums[b - 1]) b++;  // skip same result
                    while (b < e && nums[e] == nums[e + 1]) e--;  // skip same result
                }
            }
        }
        return lists;
    }
}
