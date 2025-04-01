package main;

// Node class representing each node of the AVL Tree
class Node {
    int key;
    int height;
    Node left, right;

    Node(int key) {
        this.key = key;
        height = 1;
    }
}

// AVL Tree class
public class AVLTree {
    private Node root;

    // Helper function to get the height of the node
    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    // Delete a key from the AVL tree
    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node node, int key) {
        // Perform the normal BST deletion
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // Node with only one child or no child
            if ((node.left == null) || (node.right == null)) {
                Node temp = (node.left != null) ? node.left : node.right;

                // No child case
                if (temp == null) {
                    node = null;
                } else { // One child case
                    node = temp;
                }
            } else {
                // Node with two children: Get the inorder successor (smallest in the right subtree)
                Node temp = getMinValueNode(node.right);
                node.key = temp.key;
                node.right = delete(node.right, temp.key);
            }
        }


        // If the tree had only one node, return
        if (node == null) {
            return null;
        }

        // Update the height of the current node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // Get the balance factor of this node
        int balance = getBalance(node);

        // Perform rotations to balance the tree
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node getMinValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }


    // Helper function to get the balance factor of the node
    private int getBalance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    // Right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T = x.right;

        x.right = y;
        y.left = T;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotation
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T = y.left;

        y.left = x;
        x.right = T;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a key into the AVL tree
    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
        // Perform the normal BST insertion
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node; // Duplicate keys not allowed
        }

        // Update the height of the ancestor node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // Get the balance factor
        int balance = getBalance(node);

        // Perform rotations to fix the balance factor if necessary
        if (balance > 1 && key < node.left.key) {
            return rotateRight(node);
        }
        if (balance < -1 && key > node.right.key) {
            return rotateLeft(node);
        }
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // In-order traversal of the tree
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.key + " ");
            inOrderTraversal(node.right);
        }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        System.out.println("In-order traversal of the AVL tree:");
        tree.inOrderTraversal();

        System.out.println("\nDeleting 20...");
        tree.delete(20);
        System.out.println("In-order traversal after deletion:");
        tree.inOrderTraversal();
    }

}
