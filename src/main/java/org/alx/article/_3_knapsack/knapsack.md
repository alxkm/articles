# Dynamic programming. Knapsack problem

![image](source/1_JX28m2KQ_Hx-ZDfSIWfebw.jpeg)

Given by N items, each item has mass w_i > 0 {w_1, w_2, …, w_n},
and cost p_i > 0 {c_1, c_3, …, c_n}.
We must choose a subset of these items where the combined weight doesn’t surpass a specified value W (backpack capacity), and the total value is maximized.

The backpack problem can be solved in several ways:

- Iterate through all subsets of a set of N items. We can include some item or not. Complexity — O(2^N)
- A greedy option where we choose the largest element and then try to get the remaining amount. Complexity — O(N LogN).
- Dynamic programming method. Complexity — O(N W)
- The “meet in the middle” method. Complexity — O(2^(n/2) N)

The most effective and interesting option for us, is the dynamic programming approach.

### Brute-force approach

The brute-force solution for the knapsack problem involves iterating through all possible combinations of items, where each combination is represented as a binary vector indicating whether each item is included (1) or not (0) in the knapsack.

The algorithm explores all possible sequences of binary vectors, where each vector represents a potential selection of items for the knapsack. By systematically iterating through these vectors, the algorithm evaluates the value of each combination and selects the one with the maximum value while ensuring that the total weight does not exceed the capacity of the knapsack.

This approach guarantees finding the optimal solution to the knapsack problem, but it can be computationally expensive, especially for large numbers of items, due to the exponential number of possible combinations to explore.

In summary, the iterative brute-force solution systematically explores all possible item combinations using binary vectors, ensuring that the optimal selection is found by exhaustive search.

```java
import java.util.ArrayList;
import java.util.List;

class Item {
    int weight;
    int value;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

public class KnapsackBruteForce {
    public static List<Item> knapsack(int W, List<Item> items) {
        int n = items.size();
        int maxVal = 0;
        int maxWeight = 0;
        List<Item> result = new ArrayList<>();

        for (int i = 0; i < (1 << n); i++) {
            int totalWeight = 0;
            int totalValue = 0;
            List<Item> currentItems = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    totalWeight += items.get(j).weight;
                    totalValue += items.get(j).value;
                    currentItems.add(items.get(j));
                }
            }

            if (totalWeight <= W && totalValue > maxVal) {
                maxVal = totalValue;
                maxWeight = totalWeight;
                result = new ArrayList<>(currentItems);
            }
        }

        System.out.println("Maximum value: " + maxVal);
        System.out.println("Total weight: " + maxWeight);
        return result;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(2, 12));
        items.add(new Item(1, 10));
        items.add(new Item(3, 20));
        items.add(new Item(2, 15));
        items.add(new Item(4, 25));

        // Knapsack capacity
        int W = 8;
        List<Item> selectedItems = knapsack(W, items);

        System.out.println("Selected items:");
        for (Item item : selectedItems) {
            System.out.println("Weight: " + item.weight + ", Value: " + item.value);
        }
    }
}
```


Full example you can find on [Github](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_3_knapsack/KnapsackBruteForce.java)

This variant will work for small sizes only because complexity is O(2^N).

### Dynamic programming approach

Let A(k, s) is a maximum value for items that can be placed in a backpack of capacity s, if only the first k can be used objects,
that is {n_1,n_2,…,n_k}, let’s call this set of admissible elements for A(k, s).

A(k, 0) = 0
A(0, s) = 0

There are 2 options to find A(k, s):

- If subject k didn’t get into the backpack. Then A(k, s) equals the maximum cost of a backpack with the same capacity and set of admissible items {n1, n2, …, n_k−1}, that is, A(k, s) = A(k − 1, s)
- If k got into the backpack. Then A(k, s) equals the maximum cost of the backpack, where weight s reduce by weight k-th item and the set of admissible items {n1, n2, …, n_k−1} plus cost k,
A(k − 1,s − w_k) + p_k

The final formula will look like:

A(k, s) = max(A(k − 1, s), A(k − 1,s − w_k) + p_k)

The cost of the required set is A(N, W), since you need to find the maximum cost of a backpack, where all items are acceptable and the capacity of the backpack is W.

### Some moments of realization

In the dynamic programming approach to solving the knapsack problem, we utilize a 2D array to store the maximum value that can be achieved with different combinations of items and capacities. We initialize the array with zeros, and then fill in the values based on the following recurrence relation:

For each item i and each capacity j:

- If the weight of item i is greater than the current capacity j, we cannot include it in the knapsack, so the maximum value remains the same as for the previous item: dp[i][j] = dp[i-1][j].
- Otherwise, we have two choices:
- Exclude item i: dp[i][j] = dp[i-1][j].
- Include item i: dp[i][j] = value[i] + dp[i-1][j — weight[i]].
  
We choose the maximum of these two options as the value for dp[i][j].

By filling the 2D array in this manner, the value at dp[n][W] (where n is the number of items and W is the knapsack capacity) represents the maximum value that can be achieved with the given items and capacity.

Here’s the basic idea:

```java
Initialize a 2D array dp[n+1][W+1] with zeros

for i from 1 to n:
    for j from 1 to W:
        if weight[i] > j:
            dp[i][j] = dp[i-1][j]
        else:
            dp[i][j] = max(dp[i-1][j], value[i] + dp[i-1][j - weight[i]])

Return dp[n][W]
```

And the full example on Java:

```java
import java.util.ArrayList;
import java.util.List;

class Item {
    int weight;
    int value;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}

public class KnapsackDP {
    public static List<Item> knapsack(int W, List<Item> items) {
        int n = items.size();
        int[][] dp = new int[n + 1][W + 1];

        // Initialize the dp table
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (items.get(i - 1).weight <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - items.get(i - 1).weight] + items.get(i - 1).value);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // Retrieve selected items
        List<Item> selectedItems = new ArrayList<>();
        int i = n, j = W;
        while (i > 0 && j > 0) {
            if (dp[i][j] != dp[i - 1][j]) {
                selectedItems.add(items.get(i - 1));
                j -= items.get(i - 1).weight;
            }
            i--;
        }

        return selectedItems;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(2, 12));
        items.add(new Item(1, 10));
        items.add(new Item(3, 20));
        items.add(new Item(2, 15));
        items.add(new Item(4, 25));

        // Knapsack capacity
        int W = 8;
        List<Item> selectedItems = knapsack(W, items);

        System.out.println("Selected items:");
        for (Item item : selectedItems) {
            System.out.println("Weight: " + item.weight + ", Value: " + item.value);
        }
    }
}
```

### For practice this approach

https://leetcode.com/problems/partition-equal-subset-sum/
https://leetcode.com/problems/target-sum/
Subset sum
Count the number of subset with a given difference
Count of subsets sum with a given sum

Full example you can find on [Github](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_3_knapsack/KnapsackDP.java).

Thanks for reading!