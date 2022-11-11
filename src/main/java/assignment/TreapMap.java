package assignment;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class TreapMap<K extends Comparable<K>, V> implements Treap<K, V> {

    private TreeNode<K, V> root;
    private int numChanges;

    public TreapMap() {
        this.numChanges = 0;
    }

    private TreapMap(TreeNode<K, V> root) {
        this.root = root;
        this.numChanges = 0;
    }

    // Retrieves the value associated with a key in this treap
    @Override
    public V lookup(K key) {
        if (key == null) {
            return null;
        }

        TreeNode<K, V> current = root;
        current = find(key, current);

        if (current != null && current.getKey().equals(key)) {
            return current.getValue();
        }

        return null;
    }

    // finds the node with this key in the treap
    private TreeNode<K, V> find(K key, TreeNode<K, V> current) {
        while (current != null && !current.getKey().equals(key)) {
            if (current.getKey().compareTo(key) < 0) {
                // the current node key is less than the key we are looking for, go into the right subtree
                current = current.getRight();
            } else {
                // the current node key is greater than the key we are looking for, go into the left subtree
                current = current.getLeft();
            }
        }
        return current;
    }

    // Adds a key-value pair to this treap
    @Override
    public void insert(K key, V value) {
        if (key == null || value == null) {
            return;
        }

        TreeNode<K, V> addedNode = new TreeNode<>(key, value, generatePriority(), null);

        insertNode(key, addedNode, true);

        numChanges++;
    }

    // insert addedNode into the treap
    private void insertNode(K key, TreeNode<K, V> addedNode, boolean replace) {
        if (root == null) {
            root = addedNode;
            return;
        }

        TreeNode<K, V> current = root;
        while (current != null) {
            if (current.getKey().compareTo(key) < 0) {
                // the current node key is less than the key we are inserting, go into the right subtree
                if (current.getRight() == null) {
                    // set it as the right child and exit
                    current.setRight(addedNode);
                    break;
                } else {
                    current = current.getRight();
                }
            } else if (replace && current.getKey().equals(key)) {
                // replace this node's value with addedNode's value. Don't need to do any more operations because
                // this node is already in the right place
                current.setValue(addedNode.getValue());
                return;
            } else {
                // the current node key is greater than or equal to the key we are inserting, go into the left subtree
                if (current.getLeft() == null) {
                    // set it as the left child and exit
                    current.setLeft(addedNode);
                    break;
                } else {
                    current = current.getLeft();
                }
            }
        }

        addedNode.setParent(current);
        current = addedNode;

        // current will store the TreeNode<K, V> that we just added into the treap do rotations upwards based on priorities
        while (current != null && current.getParent() != null) {
            if (current.getParent().getPriority() < current.getPriority()) {
                // need to rotate upward

                TreeNode<K, V> parentParent = current.getParent().getParent();
                boolean leftSide = checkLeftSide(current.getParent(), parentParent);

                if (current.getParent().getLeft() != null && current.getParent().getLeft().equals(current)) {
                    // the current node is the left child of the parent, rotate right around parent
                    rotateRight(current.getParent(), parentParent);
                } else {
                    // the current node is the right child of the parent, rotate left around parent
                    rotateLeft(current.getParent(), parentParent);
                }

                setParent(current, parentParent, leftSide);
            } else {
                break;
            }
        }

        if (current != null && current.getParent() == null) {
            root = current;
        }
    }

    // rotate left around the current node
    private void rotateLeft(TreeNode<K, V> current, TreeNode<K, V> parent) {
        TreeNode<K, V> rightChild = current.getRight();
        current.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(current);
        }
        rightChild.setLeft(current);
        current.setParent(rightChild);
        rightChild.setParent(parent);
    }

    // rotate right around the current node
    private void rotateRight(TreeNode<K, V> current, TreeNode<K, V> parent) {
        TreeNode<K, V> leftChild = current.getLeft();
        current.setLeft(leftChild.getRight());
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(current);
        }
        leftChild.setRight(current);
        current.setParent(leftChild);
        leftChild.setParent(parent);
    }

    // Removes key from this treap
    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }

        TreeNode<K, V> current = root;
        current = find(key, current);

        // did not find the key in the tree
        if (current == null || !current.getKey().equals(key)) {
            return null;
        }

        removeNode(current);

        numChanges++;

        return current.getValue();
    }

    // removes the current node from the treap
    private void removeNode(TreeNode<K, V> current) {
        // Keep rotating down until current is a leaf node
        while (current != null && (current.getLeft() != null || current.getRight() != null)) {

            TreeNode<K, V> parent = current.getParent();
            // check if the current node is on the left or right subtree of its parent
            boolean leftSide = checkLeftSide(current, parent);

            if (current.getLeft() == null) {
                rotateLeft(current, parent);
            } else if (current.getRight() == null) {
                rotateRight(current, parent);
            } else {
                if (current.getLeft().getPriority() > current.getRight().getPriority()) {
                    rotateRight(current, parent);
                } else {
                    rotateLeft(current, parent);
                }
            }

            setParent(current.getParent(), parent, leftSide);
            if (parent == null) {
                root = current.getParent();
            }
        }

        // now that it's at the bottom of the tree, we can remove it
        if (current != null && current.getParent() != null) {
            if (checkLeftSide(current, current.getParent())) {
                current.getParent().setLeft(null);
            } else {
                current.getParent().setRight(null);
            }
        }

        if (current != null && current.getParent() == null && current.getLeft() == null && current.getRight() == null) {
            root = null;
        }
    }

    // sets the parent's left or right child to current
    private void setParent(TreeNode<K, V> current, TreeNode<K, V> parent, boolean leftSide) {
        if (parent != null) {
            if (leftSide) {
                parent.setLeft(current);
            } else {
                parent.setRight(current);
            }
            current.setParent(parent);
        }
    }

    // check if the current TreeNode<K, V> is the left child of the
    private boolean checkLeftSide(TreeNode<K, V> current, TreeNode<K, V> parent) {
        return parent != null && parent.getLeft() != null && parent.getLeft().equals(current);
    }

    // Splits this treap into two treaps
    @Override
    public Treap<K, V>[] split(K key) {
        if (key == null) {
            return null;
        }

        // create the node that is supposed to perform the split
        TreeNode<K, V> splitNode = new TreeNode<>(key, null, MAX_PRIORITY, null);
        insertNode(key, splitNode, false);

        if (splitNode.getLeft() != null) splitNode.getLeft().setParent(null);
        if (splitNode.getRight() != null) splitNode.getRight().setParent(null);

        Treap<K, V>[] answer = new TreapMap[2];
        answer[0] = new TreapMap<>(splitNode.getLeft());
        answer[1] = new TreapMap<>(splitNode.getRight());

        numChanges++;

        return answer;
    }

    // Joins this treap with another treap
    @Override
    public void join(Treap<K, V> t) {
        if (t == null) {
            return;
        }

        // create a temporary root and remove it later
        TreeNode<K, V> temporaryRoot = new TreeNode<>(null, null, MAX_PRIORITY, null);
        temporaryRoot.setLeft(root);
        if (root != null) root.setParent(temporaryRoot);
        if (t instanceof TreapMap<K, V> treapMap) {
            temporaryRoot.setRight(treapMap.getRoot());
            if (treapMap.getRoot() != null) treapMap.getRoot().setParent(temporaryRoot);
            removeNode(temporaryRoot);
            treapMap.setRoot(null);
            numChanges++;
        }
    }

    public TreeNode<K, V> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<K, V> root) {
        this.root = root;
    }

    public int getNumChanges() {
        return numChanges;
    }

    // returns an iterator that iterates over the keys in sorted order
    @Override
    public Iterator<K> iterator() {
        return new TreapMapIterator(root, numChanges);
    }

    // Build a human-readable version of the treap
    @Override
    public String toString() {
        Stack<TreeNode<K, V>> stack = new Stack<TreeNode<K, V>>();

        // keeps track of the depth of the current TreeNode has been popped from the stack
        Stack<Integer> depth = new Stack<Integer>();

        if (root != null) {
            stack.push(root);
            depth.push(0);
        }

        StringBuilder stringBuilder = new StringBuilder();

        // pre-order traversal
        while (!stack.isEmpty()) {
            TreeNode<K, V> current = stack.pop();
            int d = depth.pop();
            for (int j = 0; j < d; j++) {
                stringBuilder.append("\t");
            }
            stringBuilder.append("[" + current.getPriority() + "] <" + current.getKey() + ", " + current.getValue() + ">\n");
            if (current.getRight() != null) {
                stack.push(current.getRight());
                depth.push(d + 1);
            }
            if (current.getLeft() != null) {
                stack.push(current.getLeft());
                depth.push(d+1);
            }
        }
        return stringBuilder.toString();
    }

    // class for iterator
    private class TreapMapIterator implements Iterator<K> {

        private Stack<TreeNode<K, V>> stack;
        private TreeNode<K, V> root;
        // keeps track of the number of times the current TreeNode has been popped from the stack
        private Stack<Integer> popCount;
        private int numChanges;

        public TreapMapIterator(TreeNode<K, V> root, int numChanges) {
            stack = new Stack<TreeNode<K, V>>();
            popCount = new Stack<Integer>();
            if (root != null) {
                stack.push(root);
                popCount.push(0);
            }
            this.root = root;
            this.numChanges = numChanges;
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            if (TreapMap.this.getNumChanges() != numChanges) {
                throw new ConcurrentModificationException("Treap cannot be modified while iterating");
            }
            while (!stack.isEmpty()) {
                // in-order traversal
                TreeNode<K, V> current = stack.pop();
                int count = popCount.pop();
                count++;
                if (count == 1) {
                    stack.push(current);
                    popCount.push(count);
                    // push the left child into the stack
                    if (current.getLeft() != null) {
                        stack.push(current.getLeft());
                        popCount.push(0);
                    }
                } else {
                    // push the right child into the stack
                    if (current.getRight() != null) {
                        stack.push(current.getRight());
                        popCount.push(0);
                    }

                    // current is the next key in the sequence
                    return current.getKey();
                }
            }

            throw new NoSuchElementException("No elements remaining in iterator");
        }
    }

    @Override
    public void meld(Treap<K, V> t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void difference(Treap<K, V> t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    private int generatePriority() {
        return (int)(Math.random() * MAX_PRIORITY);
    }
}