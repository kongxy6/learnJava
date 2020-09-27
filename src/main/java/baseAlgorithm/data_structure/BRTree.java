package baseAlgorithm.data_structure;

import org.junit.jupiter.api.Test;

public class BRTree {

    private final int RED = 0;
    private final int BLACK = 1;
    private BRTreeNode root;

    // 在旋转时就将根维护了
    BRTreeNode rightRotation(BRTreeNode parent) {
        BRTreeNode pp = parent.parent;
        BRTreeNode left = parent.left;
        parent.left = left.right;
        if (left.right != null) {
            parent.left.parent = parent;
        }
        left.right = parent;
        parent.parent = left;
        left.parent = pp;
        if (pp == null) {
            root = left;
        } else {
            if (pp.left == parent) {
                pp.left = left;
            } else if (pp.right == parent) {
                pp.right = left;
            }
        }
        return left;
    }

    BRTreeNode leftRotation(BRTreeNode parent) {
        BRTreeNode pp = parent.parent;
        BRTreeNode right = parent.right;
        parent.right = right.left;
        if (right.left != null) {
            right.left.parent = parent;
        }
        right.left = parent;
        parent.parent = right;
        right.parent = pp;
        if (pp == null) {
            root = right;
        } else {
            if (pp.right == parent) {
                pp.right = right;
            } else {
                pp.left = right;
            }
        }
        return right;
    }

    void insert(int value) {
        // 寻找插入位置
        BRTreeNode node = root;
        BRTreeNode parent = node;
        if (node == null) {
            root = new BRTreeNode(value);
            fixAfterInsertion(root);
            return;
        }
        while (node != null) {
            if (value < node.value) {
                parent = node;
                node = node.left;
            } else {
                parent = node;
                node = node.right;
            }
        }
        // 找到了插入位置
        if (value > parent.value) {
            parent.right = new BRTreeNode(value);
            parent.right.parent = parent;
            fixAfterInsertion(parent.right);
        } else {
            parent.left = new BRTreeNode(value);
            parent.left.parent = parent;
            fixAfterInsertion(parent.left);
        }
    }

    void delete(int value) {

    }

    void fixAfterInsertion(BRTreeNode node) {
        while (node != null && node != root && node.parent.color == RED) {
            BRTreeNode parent = node.parent;
            BRTreeNode pp = parent.parent;
            if (pp == null) {
                break;
            } else {
                if (parent == pp.left) {
                    BRTreeNode y = pp.right;
                    if (y != null && y.color == RED) {
                        parent.color = BLACK;
                        y.color = BLACK;
                        pp.color = RED;
                        node = pp;
                    } else if (y == null) {
                        if (node == parent.right) {
                            node = node.parent;
                            leftRotation(node);
                        }
                        node.parent.color = BLACK;
                        node.parent.parent.color = RED;
                        rightRotation(pp);
                    } else {
                        node = node.parent;
                        leftRotation(node);
                        node.parent.color = BLACK;
                        node.parent.parent.color = RED;
                        rightRotation(pp);
                    }
                } else {
                    BRTreeNode y = pp.left;
                    if (y != null && y.color == RED) {
                        parent.color = BLACK;
                        y.color = BLACK;
                        pp.color = RED;
                        node = pp;
                    } else {
                        if (node == parent.left) {
                            node = node.parent;
                            rightRotation(node);
                        }
                        node.parent.color = BLACK;
                        node.parent.parent.color = RED;
                        leftRotation(pp);
                    }
                }
            }
        }
        root.color = BLACK;
    }

    private void print(BRTreeNode tree, int key, int direction) {
        if (tree != null) {
            if (direction == 0)    // tree是根节点
                System.out.printf("%2d is root color = %6s\n", tree.value, tree.color == 1 ? "BLACK" : "RED");
            else                // tree是分支节点
                System.out.printf("%2d is %2d's %6s child color = %6s\n",
                        tree.value, key, direction == 1 ? "right" : "left", tree.color == 1 ? "BLACK" : "RED");
            print(tree.left, tree.value, -1);
            print(tree.right, tree.value, 1);
        }
    }

    void print() {
        print(root, root.value, 0);
    }

    @Test
    void test() {
        BRTree avlTree = new BRTree();
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};
        for (int a : arr) {
            avlTree.insert(a);
            avlTree.print();
        }
    }

    class BRTreeNode {
        int value;
        int color;
        BRTreeNode left;
        BRTreeNode right;
        BRTreeNode parent;

        BRTreeNode(int value) {
            this.value = value;
            this.color = RED;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

}
