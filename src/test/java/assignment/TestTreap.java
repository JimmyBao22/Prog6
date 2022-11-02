package assignment;

public class TestTreap {

    public static void main(String[] args) {
        Treap<Integer, Integer> treap = new TreapMap();
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
    }
}