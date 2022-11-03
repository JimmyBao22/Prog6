package assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.*;

public class TestTreapMethods {

    public static void main(String[] args) {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(6, 6);
        treap.insert(3, 3);
        treap.insert(1, 1);
        treap.insert(5, 5);
        treap.insert(8, 8);
        treap.insert(9, 9);
        treap.insert(4, 4);
        System.out.println(treap.toString());
        System.out.println();
        System.out.println(treap.remove(5));
        System.out.println();
        System.out.println(treap.toString());
        System.out.println(treap.lookup(3));
    }

    @RepeatedTest(1000)
    void testInsertLookup() {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

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
            Assertions.assertEquals(map.get(key), treap.lookup(key));
        }

        for (int i = -MAX; i <= MAX; i++) {
            if (!map.containsKey(i)) {
                Assertions.assertEquals(null, treap.lookup(i));
            } else {
                Assertions.assertEquals(map.get(i), treap.lookup(i));
            }
        }

        testStringRepresentation(treap.toString(), map);
    }

    @RepeatedTest(1000)
    void testRemove() {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

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
            Assertions.assertEquals(map.get(key), treap.lookup(key));
        }

        for (int i = -MAX; i <= MAX; i++) {
            if (!map.containsKey(i)) {
                Assertions.assertEquals(null, treap.lookup(i));
            } else {
                Assertions.assertEquals(map.get(i), treap.lookup(i));
            }
        }

        testStringRepresentation(treap.toString(), map);
    }

    @RepeatedTest(1000)
    void testSplit() {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        int MAX = 10000;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < MAX; i++) {
            // generate random key+value
            int key = (int) (Math.random() * MAX) - MAX / 2;
            int value = (int) (Math.random() * MAX) - MAX / 2;
            treap.insert(key, value);
            map.put(key, value);
        }

        int[][] arr = new int[map.size()][2];
        int i = 0;
        for (Integer key : map.keySet()) {
            arr[i][0] = key;
            arr[i++][1] = map.get(key);
        }

//        System.out.println(treap.toString());

        int splitVal = (int) (Math.random() * MAX);
        Treap<Integer, Integer>[] ret = treap.split(splitVal);

//        System.out.println(splitVal);
//        System.out.println(ret[0].toString());
//        System.out.println(ret[1].toString());

        for (i = 0; i < arr.length; i++) {
            if (arr[i][0] < splitVal) {
                Assertions.assertEquals(arr[i][1], ret[0].lookup(arr[i][0]));
                Assertions.assertTrue(ret[1].lookup(arr[i][0]) == null);
            } else {
                Assertions.assertTrue(ret[0].lookup(arr[i][0]) == null);
                Assertions.assertEquals(arr[i][1], ret[1].lookup(arr[i][0]));
            }
        }

        for (i = -MAX; i <= MAX; i++) {
            if (!map.containsKey(i)) {
                Assertions.assertEquals(null, ret[0].lookup(i));
                Assertions.assertEquals(null, ret[1].lookup(i));
            }
        }
    }

    @RepeatedTest(1000)
    void testJoin() {
        Treap<Integer, Integer> treapOne = new TreapMap<Integer, Integer>();
        Treap<Integer, Integer> treapTwo = new TreapMap<Integer, Integer>();

        int MAX = 10000;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < MAX; i++) {
            // generate random key+value (negative #'s)
            int key = (int)(Math.random() * MAX) - MAX;
            int value = (int)(Math.random() * MAX) - MAX;
            treapOne.insert(key, value);
            map.put(key, value);
        }

        for (int i = 0; i < MAX; i++) {
            // generate random key+value (positive #'s)
            int key = (int)(Math.random() * MAX);
            int value = (int)(Math.random() * MAX);
            treapTwo.insert(key, value);
            map.put(key, value);
        }

//        System.out.println(treapOne.toString());
//        System.out.println();
//        System.out.println(treapTwo.toString());
//        System.out.println();

        treapOne.join(treapTwo);

//        System.out.println(treapOne.toString());

        for (Integer key : treapOne) {
            Assertions.assertEquals(map.get(key), treapOne.lookup(key));
        }

        for (int i = -MAX; i <= MAX; i++) {
            if (!map.containsKey(i)) {
                Assertions.assertEquals(null, treapOne.lookup(i));
            } else {
                Assertions.assertEquals(map.get(i), treapOne.lookup(i));
            }
        }
    }

    @RepeatedTest(1000)
    void testIterator() {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        int MAX = 10000;

        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < MAX; i++ ) {
            // generate random key+value
            int key = (int)(Math.random() * MAX) - MAX/2;
            int value = (int)(Math.random() * MAX) - MAX/2;
            treap.insert(key, value);
            set.add(key);
        }

        int i = 0;
        int[] arr = new int[set.size()];
        for (Integer a : set) {
            arr[i++] = a;
        }
        Arrays.sort(arr);

//        i = 0;
//        for (Integer key : treap) {
//            Assertions.assertEquals(arr[i++], key);
//        }
//        Assertions.assertEquals(i, arr.length);

        i = 0;
        Iterator<Integer> it = treap.iterator();
        while (it.hasNext()) {
            Assertions.assertEquals(arr[i++], it.next());
        }
        Assertions.assertEquals(i, arr.length);
    }

    @RepeatedTest(1000)
    void testToString() {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        int MAX = 10000;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < MAX; i++ ) {
            // generate random key+value
            int key = (int)(Math.random() * MAX) - MAX/2;
            int value = (int)(Math.random() * MAX) - MAX/2;
            treap.insert(key, value);
            map.put(key, value);
        }

        String tree = treap.toString();

//        System.out.println(tree);

        testStringRepresentation(tree, map);
    }

    private void testStringRepresentation(String tree, HashMap<Integer, Integer> map) {
        String[] treeLines = tree.split("\n");
        int n = treeLines.length;
        CurNode[] treeNodes = new CurNode[n];
        for (int i = 0; i < n; i++) {
            String cur = treeLines[i];
            int numTabs = cur.indexOf('[');
            int priority = Integer.parseInt(cur.substring(cur.indexOf('[')+1, cur.indexOf(']')));
            int key = Integer.parseInt(cur.substring(cur.indexOf('<')+1, cur.indexOf(',')));
            int value = Integer.parseInt(cur.substring(cur.indexOf(',')+2, cur.indexOf('>')));
            treeNodes[i] = new CurNode(numTabs, priority, key, value);
        }

        Node root = new Node(treeNodes[0]);
        Node curr = root;
        int depth = 0;
        for (int i = 1; i < n; i++) {
            CurNode curTree = treeNodes[i];
            while (depth+1 != curTree.getNumTabs()) {
                curr = curr.getParent();
                depth--;
            }

            // curr should be the parent of this node
            if (curr.getCurNode().getKey() < curTree.getKey()) {
                // put it in the right subtree
                curr.setRight(new Node(curTree));
                curr.getRight().setParent(curr);
                curr = curr.getRight();
            } else {
                curr.setLeft(new Node(curTree));
                curr.getLeft().setParent(curr);
                curr = curr.getLeft();
            }

            depth++;
        }

        Stack<Node> stack = new Stack<Node>();
        stack.push(root);

        int count = 0;
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            count++;
            Assertions.assertEquals(map.get(cur.getCurNode().getKey()), cur.getCurNode().getValue());

            Node parent = cur.getParent();
            if (parent != null) {
                Assertions.assertTrue(parent.getCurNode().getPriority() >= cur.getCurNode().getPriority());
            }

            if (cur.getLeft() != null) {
                Assertions.assertTrue(cur.getLeft().getCurNode().getKey() < cur.getCurNode().getKey());
                stack.push(cur.getLeft());
            }
            if (cur.getRight() != null) {
                Assertions.assertTrue(cur.getRight().getCurNode().getKey() > cur.getCurNode().getKey());
                stack.push(cur.getRight());
            }
        }

        Assertions.assertEquals(map.size(), count);

        //        System.out.println();
//        Stack<Node> stack = new Stack<Node>();
//        Stack<Integer> dep = new Stack<Integer>();
//        stack.push(root);
//        dep.push(0);
//        StringBuilder stringBuilder = new StringBuilder();
//        while (!stack.isEmpty()) {
//            Node cur = stack.pop();
//            int d = dep.pop();
//            for (int j = 0; j < d; j++) {
//                stringBuilder.append("\t");
//            }
//            stringBuilder.append("[" + cur.getCurNode().getPriority() + "] <" + cur.getCurNode().getKey() + ", " + cur.getCurNode().getValue() + ">\n");
//            if (cur.getRight() != null) {
//                stack.push(cur.getRight());
//                dep.push(d + 1);
//            }
//            if (cur.getLeft() != null) {
//                stack.push(cur.getLeft());
//                dep.push(d+1);
//            }
//        }
//        System.out.println(stringBuilder.toString());
    }

    private class Node {
        private CurNode curNode;
        private Node left, right, parent;
        public Node (CurNode curNode) {
            this.curNode = curNode;
        }
        public CurNode getCurNode() {
            return curNode;
        }
        public Node getLeft() {
            return left;
        }
        public Node getRight() {
            return right;
        }
        public Node getParent() {
            return parent;
        }
        public void setLeft(Node left) {
            this.left = left;
        }
        public void setRight(Node right) {
            this.right = right;
        }
        public void setParent(Node parent) {
            this.parent = parent;
        }
    }

    private class CurNode {
        private int numTabs, priority, key, value;

        public CurNode(int numTabs, int priority, int key, int value) {
            this.numTabs = numTabs;
            this.priority = priority;
            this.key = key;
            this.value = value;
        }
        public int getNumTabs() {
            return numTabs;
        }
        public int getPriority() {
            return priority;
        }
        public int getKey() {
            return key;
        }
        public int getValue() {
            return value;
        }
    }
}