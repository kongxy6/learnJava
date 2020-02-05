package baseAlgorithm.sort;

import org.junit.jupiter.api.Test;

/**
 * @author kxy96
 */
public class Tricolour {
    public void sortColors(int[] nums) {
        int b = 0, e = nums.length - 1;
        for (int i = 0; i < nums.length; ++i) {
            if (i > e) {
                break;
            }
            if (nums[i] == 1) {

            } else if (nums[i] == 2) {
                nums[i] = nums[e];
                nums[e] = 2;
                e--;
                i--;
            } else {
                nums[i] = nums[b];
                nums[b] = 0;
                b++;
            }
        }
        return;
    }

    @Test
    public void test() {
        int[] nums = {2, 0, 2, 1, 1, 0, 2, 1, 1, 2, 1, 0, 0, 0, 0, 2, 1, 1, 0};
        sortColors(nums);
    }
}
