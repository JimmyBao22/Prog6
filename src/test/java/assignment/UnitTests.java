package assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

public class UnitTests {

    @RepeatedTest(1000)
    void testInsertLookup() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        HashMap<Integer, Integer> map = new HashMap<>();

        int MAX = 10000;
        for (int i = 0; i < MAX; i++ ) {
            // generate random key+value
            int key = (int)(Math.random() * MAX) - MAX/2;
            int value = (int)(Math.random() * MAX) - MAX/2;
            treap.insert(key, value);
            map.put(key, value);
        }

        for (Integer key : map.keySet()) {
            Assertions.assertEquals(map.get(key), treap.lookup(key),
                    "key value pair of " + key + " " + map.get(key) + " is not in the treap");
        }

        for (int i = -MAX; i <= MAX; i++) {
            if (!map.containsKey(i)) {
                Assertions.assertEquals(null, treap.lookup(i), "treap contains invalid key of " + i);
            } else {
                Assertions.assertEquals(map.get(i), treap.lookup(i),
                        "key value pair of " + i + " " + map.get(i) + " is not in the treap");
            }
        }

        verifyTreapProperties(treap, map);
    }

    @RepeatedTest(1000)
    void testRemove() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        HashMap<Integer, Integer> map = new HashMap<>();

        int MAX = 10000;
        for (int i = 0; i < MAX; i++ ) {
            // generate random key+value
            int key = (int)(Math.random() * MAX) - MAX/2;
            int value = (int)(Math.random() * MAX) - MAX/2;
            treap.insert(key, value);
            map.put(key, value);
        }

        // randomly remove elements if they exist
        for (int i = 0; i < MAX; i++) {
            int key = (int)(Math.random() * MAX) - MAX/2;
            if (map.containsKey(key)) {
                map.remove(key);
            }
            treap.remove(key);
        }

        for (Integer key : map.keySet()) {
            Assertions.assertEquals(map.get(key), treap.lookup(key),
                    "key value pair of " + key + " " + map.get(key) + " is not in the treap");
        }

        for (int i = -MAX; i <= MAX; i++) {
            if (!map.containsKey(i)) {
                Assertions.assertEquals(null, treap.lookup(i), "treap contains invalid key of " + i);
            } else {
                Assertions.assertEquals(map.get(i), treap.lookup(i),
                        "key value pair of " + i + " " + map.get(i) + " is not in the treap");
            }
        }

        verifyTreapProperties(treap, map);
    }

    @RepeatedTest(10000)
    void testInsertDuplicate() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(2, 2);
        treap.insert(3, 3);
        treap.insert(1, 100);
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 100);
        map.put(2, 2);
        map.put(3, 3);
        verifyTreapProperties(treap, map);
    }

    void verifyTreapProperties(TreapMap<Integer, Integer> treap, HashMap<Integer, Integer> map) {
        TreeNode<Integer, Integer> root = treap.getRoot();

        Stack<TreeNode<Integer, Integer>> stack = new Stack<TreeNode<Integer, Integer>>();
        stack.push(root);

        int count = 0;
        while (!stack.isEmpty()) {
            TreeNode<Integer, Integer> cur = stack.pop();
            count++;
            Assertions.assertEquals(map.get(cur.getKey()), cur.getValue(),
                    "key value pair of " + cur.getKey() + " " +
                            map.get(cur.getKey()) + " is not in the treap");

            TreeNode<Integer, Integer> parent = root;
            if (parent != null) {
                Assertions.assertTrue(parent.getPriority() >= cur.getPriority(),
                        "priority of a parent node is greater than priority of a child node");
            }

            if (cur.getLeft() != null) {
                Assertions.assertTrue(cur.getLeft().getKey() < cur.getKey(),
                        "key of a left child is not less than the key of its parent");
                stack.push(cur.getLeft());
            }
            if (cur.getRight() != null) {
                Assertions.assertTrue(cur.getRight().getKey() > cur.getKey(),
                        "the key of a right child is not greater than the key of its parent");
                stack.push(cur.getRight());
            }
        }

        Assertions.assertEquals(map.size(), count, "incorrect number of elements in treap");
    }

    @Test
    void testRightRotations() {
        /*
        TreapMap<Integer, Integer> treap;
        do {
            treap = new TreapMap<Integer, Integer>();
            treap.insert(0, 0);
            treap.insert(1, 1);
            treap.insert(2, 2);
            treap.insert(3, 3);
        } while (!(treap.getRoot().getKey() == 2 && treap.getRoot().getLeft().getKey() == 0 && treap.getRoot().getRight().getKey() == 3
                && treap.getRoot().getLeft().getRight() != null && treap.getRoot().getLeft().getRight().getKey() == 1));

        TreeNode<Integer, Integer> cur = treap.getRoot();
        
        treap.rotateRight(cur, null);
        
        TreeNode<Integer, Integer> root = cur.getParent();
        Assertions.assertEquals(0, root.getKey(), "Root of treap should be (0, 0)");
        Assertions.assertEquals(null, root.getParent(), "Parent of root of treap should be null");
        Assertions.assertEquals(null, root.getLeft(), "Left child of root of treap should be null");
        Assertions.assertEquals(2, root.getRight().getKey(), "Right child of root of treap should be (2, 2)");
        Assertions.assertEquals(1, root.getRight().getLeft().getKey(), "Left of right child of root of treap should be (1, 1)");
        Assertions.assertEquals(null, root.getRight().getLeft().getLeft(), "Children of left of right child of root of treap should be null");
        Assertions.assertEquals(null, root.getRight().getLeft().getRight(), "Children of left of right child of root of treap should be null");
        Assertions.assertEquals(3, root.getRight().getRight().getKey(), "Right of right child of root of treap should be (3, 3)");
        Assertions.assertEquals(null, root.getRight().getRight().getRight(), "Children of right of right child of root of treap should be null");
        Assertions.assertEquals(null, root.getRight().getRight().getLeft(), "Children of right of right child of root of treap should be null");
         */
    }

    @Test
    void testLeftRotations() {
        /*
        TreapMap<Integer, Integer> treap;
        do {
            treap = new TreapMap<Integer, Integer>();
            treap.insert(0, 0);
            treap.insert(1, 1);
            treap.insert(2, 2);
            treap.insert(3, 3);
        } while (!(treap.getRoot().getKey() == 1 && treap.getRoot().getLeft().getKey() == 0 && treap.getRoot().getRight().getKey() == 3
                && treap.getRoot().getRight().getLeft() != null && treap.getRoot().getRight().getLeft().getKey() == 2));

        TreeNode<Integer, Integer> cur = treap.getRoot();

        treap.rotateLeft(cur, null);

        TreeNode<Integer, Integer> root = cur.getParent();
        Assertions.assertEquals(3, root.getKey(), "Root of treap should be (3, 3)");
        Assertions.assertEquals(null, root.getParent(), "Parent of root of treap should be null");
        Assertions.assertEquals(null, root.getRight(), "Right child of root of treap should be null");
        Assertions.assertEquals(1, root.getLeft().getKey(), "Left child of root of treap should be (1, 1)");
        Assertions.assertEquals(2, root.getLeft().getRight().getKey(), "Right of left child of root of treap should be (2, 2)");
        Assertions.assertEquals(null, root.getLeft().getRight().getLeft(), "Children of left of right child of root of treap should be null");
        Assertions.assertEquals(null, root.getLeft().getRight().getRight(), "Children of left of right child of root of treap should be null");
        Assertions.assertEquals(0, root.getLeft().getLeft().getKey(), "Left of left child of root of treap should be (0, 0)");
        Assertions.assertEquals(null, root.getLeft().getLeft().getRight(), "Children of left of left child of root of treap should be null");
        Assertions.assertEquals(null, root.getLeft().getLeft().getLeft(), "Children of left of left child of root of treap should be null");
         */
    }
}
