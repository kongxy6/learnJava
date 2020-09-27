package baseAlgorithm.data_structure;

import org.junit.jupiter.api.Test;

/**
 * 对节点维护一个父指针
 */
class AVLTreeHasParent {

    // 一棵树肯定是需要一个根节点的
    private AVLTreeNode root = null;

    private int height(AVLTreeNode tree) {
        if (tree != null)
            return tree.height;
        return 0;
    }

    AVLTreeNode rightRotation(AVLTreeNode parent) {
        AVLTreeNode pp = parent.parent;
        AVLTreeNode child = parent.left;
        // 判断重型节点的位置，考虑是否进行左旋
        if (height(child.left) < height(child.right)) {
            AVLTreeNode b = child.right;
            child.right = b.left;
            if (child.right != null) {
                child.right.parent = child;
            }
            b.left = child;
            child.parent = b;
            parent.left = b;
            b.parent = parent;
            child = parent.left;
        }
        parent.left = child.right;
        if (parent.left != null) {
            parent.left.parent = parent;
        }
        child.right = parent;
        parent.parent = child;
        if (pp == null) {
            child.parent = null;
            root = child;
        } else if (pp.left == parent) {
            child.parent = pp;
            pp.left = child;
        } else {
            child.parent = pp;
            pp.right = child;
        }
        // 修正高度
        child.right.height = (height(child.right.right) > height(child.right.left)
                ? height(child.right.right) : height(child.right.left)) + 1;
        child.left.height = (height(child.left.right) > height(child.left.left)
                ? height(child.left.right) : height(child.left.left)) + 1;
        child.height = (height(child.right) > height(child.left)
                ? height(child.right) : height(child.left)) + 1;
        return child;
    }

    AVLTreeNode leftRotation(AVLTreeNode parent) {
        AVLTreeNode pp = parent.parent;
        AVLTreeNode child = parent.right;
        if (height(child.left) > height(child.right)) {
            AVLTreeNode b = child.left;
            child.left = b.right;
            if (child.left != null) {
                child.left.parent = child;
            }
            b.right = child;
            child.parent = b;
            parent.right = b;
            b.parent = parent;
            child = parent.right;
        }
        parent.right = child.left;
        if (parent.right != null) {
            parent.right.parent = parent;
        }
        child.left = parent;
        parent.parent = child;
        if (pp == null) {
            child.parent = null;
            root = child;
        } else if (pp.left == parent) {
            child.parent = pp;
            pp.left = child;
        } else {
            child.parent = pp;
            pp.right = child;
        }
        // 修正高度
        child.right.height = (height(child.right.right) > height(child.right.left)
                ? height(child.right.right) : height(child.right.left)) + 1;
        child.left.height = (height(child.left.right) > height(child.left.left)
                ? height(child.left.right) : height(child.left.left)) + 1;
        child.height = (height(child.right) > height(child.left)
                ? height(child.right) : height(child.left)) + 1;
        return child;
    }

    int mod(int a, int b) {
        return a > b ? a - b : b - a;
    }

    void insert(int value) {
        if (root == null) {
            root = new AVLTreeNode(value, null, null);
            root.height = 1;
            root.parent = null;
            return;
        }
        AVLTreeNode node = root;
        AVLTreeNode p = null;
        while (node != null) {
            if (value < node.value) {
                p = node;
                node = node.left;
            } else {
                p = node;
                node = node.right;
            }
        }
        if (value > p.value) {
            p.right = new AVLTreeNode(value, null, null);
            p.right.parent = p;
            p.right.height = 1;
        } else {
            p.left = new AVLTreeNode(value, null, null);
            p.left.parent = p;
            p.left.height = 1;
        }
        fix(p);
        testing(root);
    }

    void delete(int val) {
        // 寻找位置
        AVLTreeNode node = root;
        while (node != null) {
            if (val > node.value) {
                node = node.right;
            } else if (val < node.value) {
                node = node.left;
            } else {
                // 相等
                AVLTreeNode parent = node.parent;
                if (node.left == null && node.right == null) {
                    if (parent == null) {
                        root = null;
                        return;
                    } else {
                        node.parent = null;
                        if (parent.left == node) {
                            parent.left = null;
                        } else {
                            parent.right = null;
                        }
                    }
                } else if (node.left == null) {
                    if (parent == null) {
                        node.right.parent = null;
                        root = node.right;
                        node.right = null;
                    } else {
                        if (node == parent.left) {
                            parent.left = node.right;
                            node.right.parent = parent;
                            node.parent = null;
                            node.right = null;
                        } else {
                            parent.right = node.right;
                            node.right.parent = parent;
                            node.parent = null;
                            node.right = null;
                        }
                    }
                } else if (node.right == null) {
                    if (parent == null) {
                        node.left.parent = null;
                        root = node.left;
                        node.left = null;
                    } else {
                        if (node == parent.left) {
                            parent.left = node.left;
                            node.left.parent = parent;
                            node.parent = null;
                            node.left = null;
                        } else {
                            parent.right = node.left;
                            node.left.parent = parent;
                            node.parent = null;
                            node.left = null;
                        }
                    }
                } else {
                    AVLTreeNode minRightNode = node.right;
                    AVLTreeNode p = node;
                    while (minRightNode.left != null) {
                        p = minRightNode;
                        minRightNode = minRightNode.left;
                    }
                    node.value = minRightNode.value;
                    // 删除minRightNode，该节点无左孩子
                    if (p.left == minRightNode) {
                        p.left = minRightNode.right;
                        if (minRightNode.right != null) {
                            minRightNode.right.parent = p;
                        }
                    } else {
                        p.right = minRightNode.right;
                        if (minRightNode.right != null) {
                            minRightNode.right.parent = p;
                        }
                    }
                    parent = p;
                }
                fix(parent);
                return;
            }
        }
        testing(root);
    }

    private void fix(AVLTreeNode parent) {
        while (parent != null) {
            if (mod(height(parent.left), height(parent.right)) > 1) {
                if (height(parent.left) > height(parent.right)) {
                    parent = rightRotation(parent);
                } else {
                    parent = leftRotation(parent);
                }
            } else {
                // 修正高度
                parent.height = (height(parent.right) > height(parent.left)
                        ? height(parent.right) : height(parent.left)) + 1;
            }
            parent = parent.parent;
        }
    }

    void testing(AVLTreeNode node) {
        if (node.left != null) {
            testing(node.left);
        }
        assert mod(height(node.left), height(node.right)) <= 1;
        int height = (height(node.right) > height(node.left)
                ? height(node.right) : height(node.left)) + 1;
        assert node.height == height;
        if (node.right != null) {
            testing(node.right);
        }
    }

    private void print(AVLTreeNode tree, int key, int direction) {
        if (tree != null) {
            if (direction == 0)    // tree是根节点
                System.out.printf("%2d is root\n", tree.value, key);
            else                // tree是分支节点
                System.out.printf("%2d is %2d's %6s child height = %2d\n",
                        tree.value, key, direction == 1 ? "right" : "left", tree.height);
            print(tree.left, tree.value, -1);
            print(tree.right, tree.value, 1);
        }
    }

    void print() {
        print(root, root.value, 0);
    }

    @Test
    void test() {
        AVLTreeHasParent avlTree = new AVLTreeHasParent();
        int[] arr = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};
        for (int a : arr) {
            avlTree.insert(a);
            avlTree.print();
        }
        for (int a : arr) {
            avlTree.delete(a);
            avlTree.print();
        }
    }

    class AVLTreeNode {
        int value;
        int height;
        AVLTreeNode left;
        AVLTreeNode right;
        AVLTreeNode parent;

        AVLTreeNode(int value, AVLTreeNode left, AVLTreeNode right) {
            this.value = value;
            this.height = 1;
            this.left = left;
            this.right = right;
            this.parent = null;
        }

    }

}
