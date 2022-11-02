package assignment;

import com.sun.source.tree.Tree;

public class TreeNode<K extends Comparable<K>, V> {

    private K key;
    private V value;
    private int priority;
    private TreeNode<K, V> left, right, parent;

    public TreeNode(K key, V value, int priority, TreeNode<K, V> parent) {
        this.key = key;
        this.value = value;
        this.priority = priority;
        this.parent = parent;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }

    public TreeNode<K, V> getLeft() {
        return left;
    }

    public TreeNode<K, V> getRight() {
        return right;
    }

    public TreeNode<K, V> getParent() {
        return parent;
    }

    public void setLeft(TreeNode<K, V> left) {
        this.left = left;
    }

    public void setRight(TreeNode<K, V> right) {
        this.right = right;
    }

    public void setParent(TreeNode<K, V> parent) {
        this.parent = parent;
    }
}