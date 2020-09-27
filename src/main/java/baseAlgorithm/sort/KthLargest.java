package baseAlgorithm.sort;

/**
 * 不够平衡，会导致失控。
 */
public class KthLargest {
    private final int goal;

    private TreeNode root = null;

    public KthLargest(int k, int[] nums) {
        // 先构造一颗二叉树
        for (int i = 0; i < nums.length; ++i) {
            addTreeNode(nums[i]);
        }
        goal = k;
    }

    public int add(int val) {
        addTreeNode(val);
        TreeNode r = root;
        int k = goal;
        while (r != null) {
            if (r.right != null) {
                if (k > r.right.cnt) {
                    if (k == r.right.cnt + 1) {
                        return r.val;
                    } else {
                        k = k - r.right.cnt - 1;
                        r = r.left;
                        continue;
                    }
                } else {
                    r = r.right;
                    continue;
                }
            }
            if (r.left != null) {
                if (k == 1) {
                    return r.val;
                }
                r = r.left;
                k = k - 1;
                continue;
            }
            return r.val;
        }
        return 0;
    }

    private void addTreeNode(int i) {
        if (root == null) {
            root = new TreeNode(i);
            root.cnt = 1;
            return;
        }
        TreeNode r = root;
        TreeNode pre = r;
        while (r != null) {
            r.cnt += 1;
            pre = r;
            if (i > r.val) {
                r = r.right;
            } else {
                r = r.left;
            }
        }
        if (i > pre.val) {
            pre.right = new TreeNode(i);
            pre.right.cnt = 1;
        } else {
            pre.left = new TreeNode(i);
            pre.left.cnt = 1;
        }
    }

    public class TreeNode {
        int cnt;
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

}
