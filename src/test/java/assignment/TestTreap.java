package assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TestTreap {

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

    void testRemove() {

    }

    @RepeatedTest(1000)
    void testLookup() {
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
    }
}