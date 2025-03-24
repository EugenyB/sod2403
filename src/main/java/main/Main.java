package main;

import tree.Tree;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        var tree = new Tree<Integer>();
        tree.add(10);
        tree.add(2);
        tree.add(13);
        tree.add(4);
        tree.add(15);
        tree.add(6);
        tree.add(11);
        tree.add(7);
        tree.add(8);
        tree.add(1);


        tree.traverse();

        if (tree.contains(10)) {
            System.out.println("Yes");
        }
        if (tree.contains(12)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}
