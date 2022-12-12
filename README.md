# Treaps

A treap is a binary search tree that uses randomization to produce balanced trees. In addition to holding a key-value
pair (a map entry), each node of a treap holds a randomly chosen priority value, such that the priority values satisfy the
heap property: Each node other than the root has a priority that is at least as large as the priorities of its two children.

Note that the root of a treap is determined by the randomly chosen priorities. The node with the highest priority is the root. Thus, the root node is equally likely to
contain any of the map entries, regardless of the order in which the entries are inserted or removed. Consequently, we
expect that half of the entries will be in the left subtreap and the other half in the right subtreap. The analysis of treap
height is therefore similar to the analysis of recursion depth in quicksort
