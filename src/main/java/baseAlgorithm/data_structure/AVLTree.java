package baseAlgorithm.data_structure;

import org.junit.jupiter.api.Test;

public class AVLTree {

    /**
     * 如何判断节点执行哪种旋转方式才是关键
     */

    public AVLTreeNode root = null;

    private int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    private int height(AVLTreeNode tree) {
        if (tree != null)
            return tree.height;
        return 0;
    }

    /*
            k2
           /  \
          k1   z
         /  \
        X    y
     */
    public AVLTreeNode leftLeftRotation(AVLTreeNode k2) {
        AVLTreeNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /*
            k2
           /  \
          z   k1
             /  \
            y    X
     */
    public AVLTreeNode rightRightRotation(AVLTreeNode k2) {
        AVLTreeNode k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.right), k2.height) + 1;
        return k1;
    }

    /*
            k3
           /  \
          k1   d
         /  \
        a    X
     */
    public AVLTreeNode leftRightRotation(AVLTreeNode k3) {
        k3.left = rightRightRotation(k3.left);
        return leftLeftRotation(k3);
    }

    /*
            k3
           /  \
          d    k1
         /    /
        a    X
     */
    public AVLTreeNode rightLeftRotation(AVLTreeNode k3) {
        k3.right = leftLeftRotation(k3.right);
        return rightRightRotation(k3);
    }

    int mod(int a, int b) {
        return a > b ? a - b : b - a;
    }

    public AVLTreeNode insert(AVLTreeNode tree, int value) {
        if (tree == null) {
            tree = new AVLTreeNode(value, null, null);
            return tree;
        } else {
            if (value > tree.value) {
                tree.right = insert(tree.right, value);
                if (mod(tree.right.height, height(tree.left)) > 1) {
                    if (value > tree.right.value) {
                        tree = rightRightRotation(tree);
                    } else if (value < tree.right.value) {
                        tree = rightLeftRotation(tree);
                    }
                }
            } else if (value < tree.value) {
                tree.left = insert(tree.left, value);
                if (mod(height(tree.left), height(tree.right)) > 1) {
                    if (value > tree.left.value) {
                        tree = leftRightRotation(tree);
                    } else if (value < tree.left.value) {
                        tree = leftLeftRotation(tree);
                    }
                }
            }
        }
        tree.height = max(height(tree.left), height(tree.right)) + 1;
        return tree;
    }

    public AVLTreeNode delete(AVLTreeNode tree, int val) {
        if (tree == null) {
            return null;
        } else {
            if (val > tree.value) {
                tree.right = delete(tree.right, val);
                if (mod(height(tree.left), height(tree.right)) > 1) {
                    AVLTreeNode node = tree.left;
                    if (height(node.left) > height(node.right)) {
                        tree = leftLeftRotation(tree);
                    } else {
                        tree = leftRightRotation(tree);
                    }
                }
            } else if (val < tree.value) {
                tree.left = delete(tree.left, val);
                tree = rightRotation(tree);
            } else {
                if (tree.right == null && tree.left == null) {
                    tree = null;
                } else if (tree.right == null) {
                    tree = tree.left;
                } else if (tree.left == null) {
                    tree = tree.right;
                } else {
                    // 寻找上一个节点，交换后删除上一个节点。
                    AVLTreeNode leftMax = tree.left;
                    while (leftMax.right != null) {
                        leftMax = leftMax.right;
                    }
                    tree.value = leftMax.value;
                    leftMax.value = val;
                    tree.left = delete(tree.left, val);
                    tree = rightRotation(tree);
                }
            }
        }
        if (tree != null) {
            tree.height = max(height(tree.left), height(tree.right)) + 1;
        }
        return tree;
    }

    private AVLTreeNode rightRotation(AVLTreeNode tree) {
        if (mod(height(tree.left), height(tree.right)) > 1) {
            AVLTreeNode node = tree.right;
            if (height(node.left) > height(node.right)) {
                tree = rightLeftRotation(tree);
            } else {
                tree = rightRightRotation(tree);
            }
        }
        return tree;
    }

    private void print(AVLTreeNode tree, int key, int direction) {
        if (tree != null) {
            if (direction == 0)    // tree是根节点
                System.out.printf("%2d is root\n", tree.value, key);
            else                // tree是分支节点
                System.out.printf("%2d is %2d's %6s child height = %2d\n", tree.value, key, direction == 1 ? "right" : "left", tree.height);
            print(tree.left, tree.value, -1);
            print(tree.right, tree.value, 1);
        }
    }

    void print() {
        print(root, root.value, 0);
    }

    void insert(int val) {
        root = insert(root, val);
    }

    void delete(int val) {
        root = delete(root, val);
    }

    @Test
    void test() {
        AVLTree avlTree = new AVLTree();
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};
        for (int a : arr) {
            avlTree.insert(a);
        }
        //avlTree.delete(13);
        avlTree.print();
    }

    class AVLTreeNode {
        int value;
        int height;
        AVLTreeNode left;
        AVLTreeNode right;

        public AVLTreeNode(int value, AVLTreeNode left, AVLTreeNode right) {
            this.value = value;
            this.height = 1;
            this.left = left;
            this.right = right;
        }

    }
}
