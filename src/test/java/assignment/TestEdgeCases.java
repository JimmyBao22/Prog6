package assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestEdgeCases {

    public static void main(String[] args) {

    }

    @Test
    void testSplitGeneric() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(2, 2);
        TreapMap<Integer, Integer>[] ret = (TreapMap<Integer, Integer>[]) treap.split(1);
        System.out.println(ret[0].toString());
        System.out.println(ret[1].toString());
        System.out.println(ret[0].getRoot());
        System.out.println(ret[1].getRoot());
    }

    @Test
    void testRemoveGreatest() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(2, 2);
        treap.insert(3, 3);
        treap.insert(4, 4);
        treap.insert(5, 5);

        Treap<Integer, Integer>[]ret = treap.split(5);
        System.out.println(ret[0].toString());
        System.out.println(((TreapMap)ret[0]).getRoot());
        System.out.println();
        System.out.println(ret[1].toString());
        System.out.println(((TreapMap)ret[1]).getRoot());
    }

    @Test
    void testRemoveLeast() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(2, 2);
        treap.insert(3, 3);
        treap.insert(4, 4);
        treap.insert(5, 5);

        Treap<Integer, Integer>[] ret = treap.split(1);
        System.out.println(ret[0].toString());
        System.out.println(((TreapMap)ret[0]).getRoot());
        System.out.println();
        System.out.println(ret[1].toString());
        System.out.println(((TreapMap)ret[1]).getRoot());
    }

    @Test
    void testJoinEmpty() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(2, 2);
        treap.insert(3, 3);
        treap.insert(4, 4);
        treap.insert(5, 5);

        String before = treap.toString();
        System.out.println(before);

        TreapMap<Integer, Integer> treap2 = new TreapMap<Integer, Integer>();

        treap.join(treap2);

        String after = treap.toString();
        System.out.println(after);
        Assertions.assertEquals(before, after, "joining with an empty treap causes treap to change.");
    }

    @Test
    void testJoinEmpty2() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(2, 2);
        treap.insert(3, 3);
        treap.insert(4, 4);
        treap.insert(5, 5);

        String before = treap.toString();
        System.out.println(before);

        TreapMap<Integer, Integer> treap2 = new TreapMap<Integer, Integer>();

        treap2.join(treap);

        String after = treap2.toString();
        System.out.println(after);
        Assertions.assertEquals(before, after, "joining with an empty treap causes treap to change.");
        Assertions.assertEquals("", treap.toString(), "first treap should get destroyed.");
    }

    @Test
    void testJoinEmpty3() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        TreapMap<Integer, Integer> treap2 = new TreapMap<Integer, Integer>();
        treap.join(treap2);
        treap2.join(treap);
    }

    @Test
    void testInsertDuplicate() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(2, 2);
        treap.insert(3, 3);
        treap.insert(1, 100);
        System.out.println(treap.toString());
    }

    @Test
    void testIterateEmpty() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        Iterator<Integer> it = treap.iterator();
        Assertions.assertEquals(false, it.hasNext(), "iterator has elements albeit treap is empty");
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            it.next();
        }, "Iterator should throw no such element exception when trying to call next");
    }

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

        System.out.println("Next Test");

        treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        ret = treap.split(2);
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

    @RepeatedTest(1000)
    void splitRandomElementNotInTree() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        int MAX = 100;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < MAX; i++) {
            // generate random key+value
            int key = (int) (Math.random() * MAX) - MAX / 2;
            int value = (int) (Math.random() * MAX) - MAX / 2;
            treap.insert(key, value);
            map.put(key, value);
        }

        int splitKey;
        do {
            splitKey = (int) (Math.random() * MAX) - MAX / 2;
        } while (map.containsKey(splitKey));

        Treap<Integer, Integer>[] ret = treap.split(splitKey);

        HashMap<Integer, Integer> firstMap = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> secondMap = new HashMap<Integer, Integer>();

        for (Integer key : map.keySet()) {
            if (key < splitKey) {
                firstMap.put(key, map.get(key));
            } else {
                secondMap.put(key, map.get(key));
            }
        }

        Assertions.assertTrue(ret[0] != null, "first subtreap should not be null");
        Assertions.assertTrue(ret[1] != null, "second subtreap should not be null");

        TestTreapMethods.testStringRepresentation(ret[0].toString(), firstMap);
        TestTreapMethods.testStringRepresentation(ret[1].toString(), secondMap);
    }

    @RepeatedTest(1000)
    void splitRandomElementInTree() {
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        int MAX = 100;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < MAX; i++) {
            // generate random key+value
            int key = (int) (Math.random() * MAX) - MAX / 2;
            int value = (int) (Math.random() * MAX) - MAX / 2;
            treap.insert(key, value);
            map.put(key, value);
        }

        int splitKey;
        do {
            splitKey = (int) (Math.random() * MAX) - MAX / 2;
        } while (!map.containsKey(splitKey));

        Treap<Integer, Integer>[] ret = treap.split(splitKey);

        HashMap<Integer, Integer> firstMap = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> secondMap = new HashMap<Integer, Integer>();

        for (Integer key : map.keySet()) {
            if (key < splitKey) {
                firstMap.put(key, map.get(key));
            } else {
                secondMap.put(key, map.get(key));
            }
        }

        Assertions.assertTrue(ret[0] != null, "first subtreap should not be null");
        Assertions.assertTrue(ret[1] != null, "second subtreap should not be null");

        TestTreapMethods.testStringRepresentation(ret[0].toString(), firstMap);
        TestTreapMethods.testStringRepresentation(ret[1].toString(), secondMap);
    }

    @RepeatedTest(100)
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
        String treapString = treap.toString();
        int rootKey = Integer.parseInt(treapString.substring(treapString.indexOf('<')+1, treapString.indexOf(',')));
//        int rootKey = treap.getRoot().getKey();
        System.out.println(rootKey);
        System.out.println(treap.toString());
        System.out.println();
        treap.remove(rootKey);
        map.remove(rootKey);
        System.out.println(treap.toString());
        Assertions.assertEquals(null, treap.lookup(rootKey));

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
        System.out.println(treap.toString());
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
//        Assertions.assertEquals(null, treap.split(null),
//                "did not return null in split when entering null key");
         Assertions.assertDoesNotThrow(() -> treap.split(null), "split null throws an error");
    }

    @Test
    void testConcurrentModificationException() {
        Treap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        treap.insert(1, 1);
        treap.insert(0, 0);
        treap.insert(2, 2);
        Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            for (Integer key : treap) {
                treap.insert(3, 3);
            }
        }, "Iterator should throw concurrent modification exception when trying to modify while iterating");
        Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            for (Integer key : treap) {
                treap.remove(1);
            }
        }, "Iterator should throw concurrent modification exception when trying to modify while iterating");
    }

    @Test
    void testRandomGenerics() {
        Treap<Integer, Integer> treap0 = new TreapMap<>();
        treap0.insert(1, 2);
        Assertions.assertEquals(2, treap0.lookup(1),
                "key value pair of " + "hello" + " " + "bye" + " is not in the treap");
        Assertions.assertEquals(null, treap0.lookup(3),
                "did not return null in lookup when entering invalid key");
        treap0.remove(1);
        Assertions.assertEquals(null, treap0.lookup(1),
                "did not return null in lookup when entering invalid key");

        Treap<String, String> treap = new TreapMap<>();
        treap.insert("hello", "bye");
        Assertions.assertEquals("bye", treap.lookup("hello"),
                "key value pair of " + "hello" + " " + "bye" + " is not in the treap");
        Assertions.assertEquals(null, treap.lookup("hell"),
                "did not return null in lookup when entering invalid key");
        treap.remove("hello");
        Assertions.assertEquals(null, treap.lookup("hello"),
                "did not return null in lookup when entering invalid key");

        Treap<Character, Character> treap2 = new TreapMap<>();
        treap2.insert('a', '?');
        Assertions.assertEquals('?', treap2.lookup('a'),
                "key value pair of " + 'a' + " " + '?' + " is not in the treap");
        Assertions.assertEquals(null, treap2.lookup('b'),
                "did not return null in lookup when entering invalid key");
        treap2.remove('a');
        Assertions.assertEquals(null, treap2.lookup('a'),
                "did not return null in lookup when entering invalid key");

        Treap<Custom, Integer> treap3 = new TreapMap<>();
        treap3.insert(new Custom(1, 2), 3);
        Assertions.assertEquals(3, treap3.lookup(new Custom(1, 2)),
                "key value pair is not in the treap");
        Assertions.assertEquals(null, treap3.lookup(new Custom(0, 2)),
                "did not return null in lookup when entering invalid key");
        treap3.remove(new Custom(1, 2));
        Assertions.assertEquals(null, treap3.lookup(new Custom(1, 2)),
                "did not return null in lookup when entering invalid key");
    }

    class Custom implements Comparable<Custom> {
        int a, b;
        Custom (int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }

        public boolean equals(Object other) {
            if (other instanceof Custom) {
                return a == ((Custom) other).getA() && b == ((Custom) other).getB();
            }
            return false;
        }

        public int compareTo(Custom other) {
            return 0;
        }
    }
}