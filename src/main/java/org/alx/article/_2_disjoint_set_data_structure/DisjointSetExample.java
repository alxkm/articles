package org.alx.article._2_disjoint_set_data_structure;

import java.util.HashMap;
import java.util.Map;

public class DisjointSetExample {
    private Map<Integer, Node> map = new HashMap<>();

    public void makeSet(int data) {
        Node node = new Node(data);
        map.put(data, node);
    }

    public int findSet(int data) {
        return findSet(map.get(data)).data;
    }

    private Node findSet(Node node) {
        if (node.parent != node) {
            node.parent = findSet(node.parent);
        }
        return node.parent;
    }

    public void union(int data1, int data2) {
        Node node1 = map.get(data1);
        Node node2 = map.get(data2);

        Node parent1 = findSet(node1);
        Node parent2 = findSet(node2);

        if (parent1 != parent2) {
            if (parent1.rank >= parent2.rank) {
                parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
                parent2.parent = parent1;
            } else {
                parent1.parent = parent2;
            }
        }
    }

    private static class Node {
        int data;
        Node parent;
        int rank;

        Node(int data) {
            this.data = data;
            this.parent = this;
            this.rank = 0;
        }
    }

    public static void main(String[] args) {
        DisjointSetExample disjointSet = new DisjointSetExample();

        disjointSet.makeSet(1);
        disjointSet.makeSet(2);
        disjointSet.makeSet(3);
        disjointSet.makeSet(4);
        disjointSet.makeSet(5);
        disjointSet.makeSet(6);
        disjointSet.makeSet(7);

        disjointSet.union(1, 2);
        disjointSet.union(2, 3);
        disjointSet.union(4, 5);
        disjointSet.union(6, 7);
        disjointSet.union(5, 6);
        disjointSet.union(3, 7);

        System.out.println("Representative element of 1: " + disjointSet.findSet(1));
        System.out.println("Representative element of 2: " + disjointSet.findSet(2));
        System.out.println("Representative element of 3: " + disjointSet.findSet(3));
        System.out.println("Representative element of 4: " + disjointSet.findSet(4));
        System.out.println("Representative element of 5: " + disjointSet.findSet(5));
        System.out.println("Representative element of 6: " + disjointSet.findSet(6));
        System.out.println("Representative element of 7: " + disjointSet.findSet(7));
    }
}
