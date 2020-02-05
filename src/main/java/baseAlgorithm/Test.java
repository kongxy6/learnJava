package baseAlgorithm;

import baseAlgorithm.algorithm.LongestPalindrome;
import baseAlgorithm.algorithm.Permute;
import baseAlgorithm.sort.KthLargest;

import java.util.List;

public class Test {

    @org.junit.jupiter.api.Test
    void test() {
        int k = 3;
        int[] nums = {4, 5, 8, 2};
        KthLargest obj = new KthLargest(k, nums);
        int param_1 = obj.add(3);
        int param_2 = obj.add(5);
    }

    @org.junit.jupiter.api.Test
    public void test_1() {
        Permute p = new Permute();
        int[] nums = {1, 2, 3};
        List<List<Integer>> ll = p.permute(nums);
        System.out.println(ll);
    }

    @org.junit.jupiter.api.Test
    public void test_2() {
        String s = "babad";
        LongestPalindrome.longestPalindrome(s);
    }

}
