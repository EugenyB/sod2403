package tree;

public class AVLTree {

    private NodeAVL root;

    private class NodeAVL {
        int key;
        NodeAVL left;
        NodeAVL right;
        int height;

        NodeAVL(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    public void traverse() {
        traverse(root);
        System.out.println();
    }

    private void traverse(NodeAVL root) {
        if (root != null) {
            traverse(root.left);
            visit(root);
            traverse(root.right);
        }
    }

    private void visit(NodeAVL root) {
        System.out.print(root.key + " ");
    }

    private int height(NodeAVL node) {
        return node == null ? 0 : node.height;
    }

    private int balanceFactor(NodeAVL node) {
        return node == null ? 0 : height(node.right) - height(node.left);
    }

    private NodeAVL rotateRight(NodeAVL y) {
        NodeAVL x = y.left;
        NodeAVL t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private NodeAVL rotateLeft(NodeAVL x) {
        NodeAVL y = x.right;
        NodeAVL t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void add(int key) {
        root = insert(root, key);
    }

    private NodeAVL insert(NodeAVL node, int key) {
        if (node == null) {
            return new NodeAVL(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = balanceFactor(node);

        if (balance > 1 && key > node.right.key) {
            return rotateLeft(node);
        }

        if (balance < -1 && key < node.left.key) {
            return rotateRight(node);
        }

        if (balance > 1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        if (balance < -1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        return node;
    }

    public boolean contains(int key) {
        return search(root, key);
    }

    private boolean search(NodeAVL node, int key) {
        if (node == null) {
            return false;
        }

        if (key == node.key) {
            return true;
        }

        if (key < node.key) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    public void remove(int key) {
        root = delete(root, key);
    }

    private NodeAVL delete(NodeAVL node, int key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                NodeAVL successor = findMin(node.right);
                node.key = successor.key;
                node.right = delete(node.right, successor.key);
            }
        }

        if (node == null) {
            return null;
        }

        node.height = Integer.max(height(node.left), height(node.right)) + 1;

        int balance = balanceFactor(node);

        if (balance > 1 && balanceFactor(node.right) >= 0) {
            return rotateLeft(node);
        }

        if (balance > 1 && balanceFactor(node.right) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        if (balance < -1 && balanceFactor(node.left) <= 0) {
            return rotateRight(node);
        }

        if (balance < -1 && balanceFactor(node.left) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        return node;
    }

    private NodeAVL findMin(NodeAVL node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}