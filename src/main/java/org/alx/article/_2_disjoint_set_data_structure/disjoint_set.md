# Disjoint set union data structure

A disjoint set union (union find) is a data structure enabling the merging of separate sets and addressing different inquiries concerning them, such as determining if elements a and b belong to the same set and finding out the size of the set.

Structure

Sets of elements will be represented as trees: each tree represents a single set. The root of each tree serves as the representative or leader of the set, and we will consider the roots as their own ancestors.

Let’s make an array p, in which for each element we store the number of its ancestor in the tree:

```java
int[] p = new int[maxn];

for (int i = 0; i < n; i++) {
    p[i] = i;
}
```

To inquire about the set containing the element «v» you must traverse the links to reach the root node:

```java
int leader(int v) {
    return (p[v] == v) ? v : leader(p[v]);
}
```

To combine two sets, you need to hang the root of one by the root of the other:

```java
void unite(int a, int b) {
    a = leader(a);
    b = leader(b);
    p[a] = b;
}
```

But this works in worst case on o(n), adding on and on new vertex, which becomes like linked list, which is slow. We improve this using some tricks.

Path compression heuristic: We can enhance the leader function by implementing path compression. Instead of directly returning the root node, we will update the parent pointers along the path to point directly to the root node, thereby compressing the path to improve future lookups.

```java
int leader(int v) {
    return (p[v] == v) ? v : (p[v] = leader(p[v]));
}
```

Rank heuristic involves storing the rank of each vertex, which signifies the height of its subtree. When merging trees, we prioritize the root of the new tree as the one with the highest rank. This adjustment of ranks ensures that the leader’s rank increases by one if it equals the rank of another vertex. Ultimately, this heuristic aims to optimize the height of the trees.

```java
void unite(int a, int b) {
    a = leader(a);
    b = leader(b);
    if (h[a] > h[b]) {
        int temp = a;
        a = b;
        b = temp;
    }
    h[b] = Math.max(h[b], h[a] + 1);
    p[a] = b;
}
```

Weight heuristic involves tracking the sizes of subtrees for each vertex instead of using ranks. During merging, we’ll allocate the subtrees to the vertex with the larger size. And this is useful for all tasks, because we need it usually for solving some tasks, by themselves.

Now our solution works O(n log n) on average. But in worst case it will be O(n).

Try to practice on next leetcode tasks:

https://leetcode.com/problems/lexicographically-smallest-equivalent-string/

https://leetcode.com/problems/number-of-provinces/

https://leetcode.com/problems/accounts-merge/
https://leetcode.com/problems/find-all-people-with-secret/

https://leetcode.com/problems/greatest-common-divisor-traversal/

Full example you can find on [Github](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_2_disjoint_set_data_structure/DisjointSetExample.java).