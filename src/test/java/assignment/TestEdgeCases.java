package assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;

public class TestEdgeCases {

    @Test
    void testSplitOne() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        Treap<Integer, Integer>[] ret = treap.split(1);
        Assertions.assertTrue(ret[0] != null, "first subtreap should not be null");
        Assertions.assertTrue(ret[1] != null, "second subtreap should not be null");
        System.out.println(ret[0].toString());
        System.out.println();
        System.out.println(ret[1].toString());
    }

    @Test
    void testSplitZero() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        Treap<Integer, Integer>[] ret = treap.split(1);
        Assertions.assertTrue(ret[0] != null, "first subtreap should not be null");
        Assertions.assertTrue(ret[1] != null, "second subtreap should not be null");
        System.out.println(ret[0].toString());
        System.out.println();
        System.out.println(ret[1].toString());
    }

    @Test
    void testRemoveRoot() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        HashMap<Integer, Integer> map = new HashMap<>();
        treap.insert(6, 6);
        map.put(6, 6);
        treap.insert(3, 3);
        map.put(3, 3);
        treap.insert(1, 1);
        map.put(1, 1);
        treap.insert(5, 5);
        map.put(5, 5);
        treap.insert(8, 8);
        map.put(8, 8);
        treap.insert(9, 9);
        map.put(9, 9);
        treap.insert(4, 4);
        map.put(4, 4);
        int rootKey = treap.getRoot().getKey();
        System.out.println(treap.toString());
        System.out.println();
        treap.remove(rootKey);
        map.remove(rootKey);
        System.out.println(treap.toString());

        TestTreapMethods.testStringRepresentation(treap.toString(), map);
    }

    @Test
    void testNull() {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(0, 0);
        treap.insert(2, 2);
        Assertions.assertEquals(null, treap.lookup(null),
                "did not return null in lookup when entering null key");
        Assertions.assertEquals(null, treap.lookup(3),
                "did not return null in lookup when entering invalid key");
        treap.insert(null, null);
        treap.insert(null, 3);
        treap.insert(4, null);
        Iterator<Integer> it = treap.iterator();
        while (it.hasNext()) {
            Integer key = it.next();
            Assertions.assertTrue(key != null, "discovered null key in treap");
            Assertions.assertTrue(key != 4, "discovered invalid key in treap");
        }
        Assertions.assertEquals(null, treap.remove(4),
                "did not return null in remove when entering invalid key");
        Assertions.assertEquals(null, treap.remove(null),
                "did not return null in remove when entering null key");
        Assertions.assertEquals(null, treap.split(null),
                "did not return null in split when entering null key");
    }
}