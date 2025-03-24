package tree;

public class Tree <T extends Comparable<T>> {
    private Node<T> root = null;

    public boolean isEmpty() {
        return root == null;
    }

    public boolean add(T value) {
        if (isEmpty()) {
            root = new Node<>(value);
            return true;
        }
        return addInSubTree(value, root);
    }

    private boolean addInSubTree(T value, Node<T> root) {
        if (value.equals(root.getKey())) {
            return false;
        }
        if (value.compareTo(root.getKey()) < 0) {
            if (root.getLeft() == null) {
                root.setLeft(new Node<>(value));
                return true;
            } else {
                return addInSubTree(value, root.getLeft());
            }
        } else {
            if (root.getRight() == null) {
                root.setRight(new Node<>(value));
                return true;
            } else {
                return addInSubTree(value, root.getRight());
            }
        }
    }

    public void traverse() {
        traverse(root);
        System.out.println();
    }

    private void traverse(Node<T> root) {
        if (root != null) {
            traverse(root.getLeft());
            visit(root);
            traverse(root.getRight());
        }
    }

    private void visit(Node<T> root) {
        System.out.print(root.getKey() + " ");
    }

    public boolean contains(T value) {
        return find(value) != null;
    }

    private Node<T> find(T value) {
        if (isEmpty()) {
            return null;
        }
        return findInSubTree(value, root);
    }

    private Node<T> findInSubTree(T value, Node<T> root) {
        if (root == null || value.equals(root.getKey())) {
            return root;
        }
        if (value.compareTo(root.getKey()) < 0) {
            return findInSubTree(value, root.getLeft());
        } else {
            return findInSubTree(value, root.getRight());
        }
    }

//    public void remove(T value) {
//        remove(value, root);
//    }
//
//    private Node<T> remove(T value, Node<T> root) {
//        if (root == null) return root;
//        if (value.equals(root.getKey())) {
//            if (root.isLeaf()) {
//                root = null;
//            } else if (root.getLeft() != null) {
//
//            }
//        } else if (value.compareTo(root.getKey()) < 0) {
//            root.setLeft(remove(value, root.getLeft()));
//        } else {
//            root.setRight(remove(value, root.getRight()));
//        }
//        return root;
//    }
}
