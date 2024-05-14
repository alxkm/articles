# Prefix Sums and Applications

In the realm of computer science and mathematics, efficient algorithms play a crucial role in solving complex problems. One such algorithmic technique that finds extensive application in various domains is prefix sums. Originally designed for one-dimensional arrays, prefix sums have been extended to cater to higher dimensions, notably in the form of 2-dimensional prefix sums, which offer remarkable solutions to a wide array of computational challenges. In this article, we delve into the concepts of prefix sums, particularly focusing on half intervals.

### Prefix Sums: A Primer

Prefix sums, also known as cumulative sums, are a fundamental concept in computer science and mathematics. The essence of prefix sums lies in the idea of precomputing cumulative totals of elements in an array up to a certain index. This precomputation allows for swift calculation of sums of subarrays, significantly enhancing efficiency in various algorithms and computations.

The process of computing prefix sums is straightforward. Given an array a of length n, the prefix sum, on half interval of array prefix can be generated as follows:

```java
prefix[0] = 0
prefix[i] = a[0] + a[1] + ... + a[i - 1]
prefix[i + 1] = a[0] + a[1] + ... + a[i - 1] + ar[i]
prefix[i + 1] = prefix[i] + a[i]
```

Let’s assume that we have such array:

```java
a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
```

Than, our prefix sum array:

```java
prefix = [0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55]
```

Half interval is usefully to avoid such kind of indexes prefix[i — 1]. And in mathematical meaning this will be half interval [left, right)

```java
sum_on_interval = prefix[right] - prefix[left]
```

Calculation:


```java
int[] findPrefix(int[] a) {
  int n = a.length;
  int[] prefix = new int[n + 1];
  for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + a[i];
  }
  return prefix;
}
```

To calculate sum from [l, r) you just need to do the following:

```java
prefix[r] - prefix[l]
```

Using half intervals [l, r), instead intervals [l, r]. Helps us avoiding corner cases prefix[right] - prefix[left -1] and handle corner cases left == 0.

Creation of prefix sum takes O(N) by time and O(N) memory (as we need an array of length N). Sum calculating on prefix array takes O(1) complexity. It is useful for some applications as additional data structure.

As an example you could try to apply this approach on https://leetcode.com/problems/product-of-array-except-self/

Full example you can find on my Github. [Github](https://github.com/alxkm/articles/blob/master/src/main/java/org/alx/article/_1_prefix_sum/PrefixSumExample.java)

Thank you for your attention!