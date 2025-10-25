### INIT 
# Minimum Spanning Tree Algorithms: Analytical Report
## Comparative Analysis of Prim's and Kruskal's Algorithms

**Course:** Data Structures and Algorithms  
**Project:** MST Algorithm Implementation and Analysis

---

## Executive Summary

This report presents a comprehensive analysis of two fundamental algorithms for computing Minimum Spanning Trees (MST): **Prim's Algorithm** and **Kruskal's Algorithm**. Both algorithms were implemented in Java and tested on 30 graphs ranging from 5 to 3,000 vertices with varying densities (0.10 to 0.60). The empirical results demonstrate that while both algorithms correctly compute MSTs, **Kruskal's algorithm exhibits superior performance on medium to large graphs**, achieving execution times up to **78 times faster** than Prim's algorithm on graphs with 3,000 vertices.

---

## 1. Summary of Input Data and Algorithm Results

### 1.1 Dataset Overview

The experimental dataset consists of **30 graphs** with the following characteristics:

| Category | Vertex Range | Number of Graphs | Density Range | Purpose |
|----------|--------------|------------------|---------------|---------|
| **Small** | 5-50 | 6 | 0.20-0.60 | Correctness verification |
| **Medium** | 75-500 | 13 | 0.15-0.35 | Performance comparison |
| **Large** | 600-3000 | 11 | 0.10-0.30 | Scalability testing |

**Graph Properties:**
- Vertices (V): 5 to 3,000 nodes
- Edges (E): 4 to 899,700 edges
- Density: 0.10 (sparse) to 0.60 (dense)
- All graphs are connected and undirected
- Edge weights range from 1 to 100

### 1.2 Algorithm Results - Small Graphs (5-50 vertices)

| Graph ID | Vertices | Edges | Density | MST Cost | Prim Time (ms) | Kruskal Time (ms) | Prim Ops | Kruskal Ops |
|----------|----------|-------|---------|----------|----------------|-------------------|----------|-------------|
| 1 | 5 | 4 | 0.40 | 176.00 | 0.303 | 2.521 | 17 | 16 |
| 2 | 10 | 22 | 0.49 | 199.00 | 0.061 | 0.039 | 77 | 173 |
| 3 | 15 | 42 | 0.40 | 354.00 | 0.118 | 0.084 | 143 | 378 |
| 5 | 30 | 217 | 0.50 | 335.00 | 0.677 | 0.500 | 591 | 2,447 |
| 6 | 50 | 245 | 0.20 | 760.00 | 0.723 | 0.220 | 739 | 2,807 |

**Key Observations:**
- Both algorithms complete in under 3 milliseconds
- Performance differences are negligible for small graphs
- Prim's algorithm performs fewer operations but Kruskal often executes faster
- MST costs are identical (correctness verified)

### 1.3 Algorithm Results - Medium Graphs (75-500 vertices)

| Graph ID | Vertices | Edges | Density | MST Cost | Prim Time (ms) | Kruskal Time (ms) | Speedup | Prim Ops | Kruskal Ops |
|----------|----------|-------|---------|----------|----------------|-------------------|---------|----------|-------------|
| 7 | 75 | 832 | 0.30 | 500.00 | 2.558 | 0.555 | 4.61x | 2,129 | 11,069 |
| 8 | 100 | 1,237 | 0.25 | 582.00 | 3.439 | 0.743 | 4.63x | 3,141 | 17,241 |
| 9 | 125 | 2,712 | 0.35 | 429.00 | 2.819 | 1.372 | 2.05x | 6,365 | 39,920 |
| 10 | 150 | 3,352 | 0.30 | 455.00 | 3.922 | 1.433 | 2.74x | 7,839 | 49,603 |
| 15 | 300 | 17,940 | 0.40 | 489.00 | 27.437 | 8.072 | 3.40x | 38,795 | 276,535 |
| 17 | 400 | 15,960 | 0.20 | 850.00 | 16.485 | 7.083 | 2.33x | 35,423 | 245,008 |
| 19 | 500 | 27,445 | 0.22 | 800.00 | 38.686 | 12.221 | 3.17x | 59,469 | 424,590 |

**Key Observations:**
- Kruskal's algorithm shows clear performance advantage (2x-4.6x faster)
- Performance gap widens as graph size increases
- Kruskal performs significantly more operations but each operation is simpler
- Consistent MST cost verification across both algorithms

### 1.4 Algorithm Results - Large Graphs (600-3000 vertices)

| Graph ID | Vertices | Edges | Density | MST Cost | Prim Time (ms) | Kruskal Time (ms) | Speedup | Prim Ops | Kruskal Ops |
|----------|----------|-------|---------|----------|----------------|-------------------|---------|----------|-------------|
| 21 | 700 | 61,162 | 0.25 | 884.00 | 192.316 | 19.922 | 9.65x | 129,333 | 950,799 |
| 23 | 900 | 72,819 | 0.18 | 1,166.00 | 163.553 | 16.438 | 9.95x | 154,501 | 1,134,359 |
| 24 | 950 | 67,616 | 0.15 | 1,306.00 | 145.826 | 14.427 | 10.11x | 144,381 | 1,052,502 |
| 26 | 1,500 | 112,425 | 0.10 | 2,006.00 | 494.426 | 31.440 | 15.72x | 239,541 | 1,749,288 |
| 30 | 3,000 | 899,700 | 0.20 | 3,007.00 | 22,674.203 | 290.042 | **78.16x** | 1,830,627 | 14,044,124 |

**Key Observations:**
- Dramatic performance advantage for Kruskal (9.65x to 78.16x faster)
- Prim's execution time grows significantly with graph size
- Kruskal maintains reasonable performance even on very large graphs
- The 3,000-vertex graph shows the most extreme performance difference

### 1.5 Aggregate Statistics

**Overall Performance Summary:**

| Metric | Prim's Algorithm | Kruskal's Algorithm | Advantage |
|--------|------------------|---------------------|-----------|
| **Total Execution Time** | 45,550.84 ms | 638.30 ms | **Kruskal 71.4x faster** |
| **Average Time per Graph** | 1,518.36 ms | 21.28 ms | **Kruskal 71.4x faster** |
| **Total Operations** | 4,475,035 | 68,387,869 | Prim 15.3x fewer |
| **Graphs Won (faster)** | 1 / 30 (3.3%) | 29 / 30 (96.7%) | **Kruskal dominant** |
| **Cost Accuracy** | 100% | 100% | Both correct |

---

## 2. Comparative Analysis: Theory vs. Practice

### 2.1 Theoretical Time Complexity

#### Prim's Algorithm

**Implementation:** Priority Queue (Binary Heap) + Adjacency List

| Operation | Frequency | Cost per Operation | Total Complexity |
|-----------|-----------|-------------------|------------------|
| Initialize | Once | O(V) | O(V) |
| Extract-Min | V times | O(log V) | O(V log V) |
| Decrease-Key | E times | O(log V) | O(E log V) |
| **Overall** | — | — | **O(E log V)** |

**Alternative with Fibonacci Heap:** O(E + V log V)

#### Kruskal's Algorithm

**Implementation:** Union-Find (Disjoint Set) + Edge Sorting

| Operation | Frequency | Cost per Operation | Total Complexity |
|-----------|-----------|-------------------|------------------|
| Sort Edges | Once | O(E log E) | O(E log E) |
| Union-Find | E times | O(α(V)) ≈ O(1) | O(E) |
| **Overall** | — | — | **O(E log E) ≈ O(E log V)** |

**Note:** Since E ≤ V², we have log E ≤ 2 log V, making both algorithms theoretically equivalent at **O(E log V)**.

### 2.2 Theoretical Space Complexity

| Algorithm | Data Structures | Space Complexity |
|-----------|----------------|------------------|
| **Prim's** | Priority Queue + Key Array + Parent Array | **O(V)** |
| **Kruskal's** | Edge List + Union-Find (parent + rank) | **O(V + E)** |

Prim's algorithm is more space-efficient, especially for dense graphs.

### 2.3 Practical Performance Analysis

Despite similar theoretical complexity, our empirical results show **significant practical differences**:

#### Graph Size vs. Execution Time

```
Small Graphs (< 50V):
  - Prim:    Avg 0.376 ms
  - Kruskal: Avg 0.673 ms
  - Winner:  Prim (1.79x faster)

Medium Graphs (50-500V):
  - Prim:    Avg 10.45 ms
  - Kruskal: Avg 3.25 ms
  - Winner:  Kruskal (3.22x faster)

Large Graphs (> 500V):
  - Prim:    Avg 2,879 ms
  - Kruskal: Avg 41.1 ms
  - Winner:  Kruskal (70.07x faster)
```

#### Why Kruskal Outperforms in Practice

**1. Operation Efficiency:**
- Union-Find operations are **extremely fast** with path compression and union by rank
- Amortized time per operation: O(α(V)) where α is the inverse Ackermann function
- In practice, α(V) ≤ 4 for any realistic V, making it effectively O(1)

**2. Cache Locality:**
- Kruskal processes edges sequentially after sorting
- Better CPU cache utilization
- Prim's requires random access to the priority queue

**3. Implementation Constants:**
- Priority queue operations have higher constant factors
- Extract-Min and Decrease-Key involve tree restructuring
- Union-Find operations are simple array accesses

**4. Sorting Efficiency:**
- Modern sorting algorithms (like Timsort in Java) are highly optimized
- One-time O(E log E) cost is amortized across all edges

### 2.4 Operation Count Analysis

Despite Kruskal performing **15.3x more operations** on average, it executes faster because:

| Factor | Prim's Operations | Kruskal's Operations |
|--------|-------------------|----------------------|
| **Type** | Priority queue manipulations | Array accesses and comparisons |
| **Complexity** | O(log V) per operation | O(α(V)) ≈ O(1) per operation |
| **Cost** | Tree restructuring | Simple pointer updates |
| **Cache** | Random memory access | Sequential access patterns |

**Example (Graph 30, 3000V):**
- Prim: 1,830,627 operations @ O(log 3000) ≈ 12 comparisons each
- Kruskal: 14,044,124 operations @ O(1) ≈ 1-2 comparisons each
- Result: Kruskal 78x faster despite 7.67x more operations

---

## 3. Conclusions and Recommendations

### 3.1 Algorithm Selection Guidelines

#### Use Prim's Algorithm When:

✓ **Graph is very small** (< 20 vertices)
  - Negligible performance difference
  - Simpler to understand and debug
  
✓ **Graph is stored as adjacency matrix**
  - Can achieve O(V²) with simple implementation
  - No need for priority queue
  
✓ **Memory is severely constrained**
  - Lower space complexity O(V) vs O(V + E)
  
✓ **Incremental MST construction is needed**
  - Can build MST from a specific starting vertex
  - Useful for network expansion scenarios

#### Use Kruskal's Algorithm When:

✓ **Graph is medium to large** (> 50 vertices) ✅ **STRONGLY RECOMMENDED**
  - 3x to 78x performance improvement observed
  - Scales better with increasing size
  
✓ **Graph is sparse** (E ≈ V)
  - Sorting cost is minimized
  - Union-Find operations dominate
  
✓ **Edges are pre-sorted or can be processed in order**
  - Can skip sorting step entirely
  
✓ **Parallel processing is available**
  - Sorting can be parallelized
  - Edge processing can be distributed
  
✓ **Production systems with performance requirements**
  - More reliable performance characteristics
  - Better worst-case behavior in practice

### 3.2 Graph Density Considerations

Our analysis reveals density has minimal impact on algorithm choice:

| Density Range | Prim Avg (ms) | Kruskal Avg (ms) | Recommendation |
|---------------|---------------|------------------|----------------|
| **Sparse (0.10-0.15)** | 214.8 | 16.4 | Kruskal (13.1x faster) |
| **Medium (0.20-0.30)** | 2,651.4 | 37.9 | Kruskal (70.0x faster) |
| **Dense (0.40-0.60)** | 5,407.3 | 85.7 | Kruskal (63.1x faster) |

**Conclusion:** Kruskal's algorithm outperforms across **all density ranges** for graphs > 50 vertices.

### 3.3 Implementation Complexity

| Aspect | Prim's Algorithm | Kruskal's Algorithm | Winner |
|--------|------------------|---------------------|--------|
| **Code Complexity** | Medium | Low | Kruskal |
| **Data Structures** | Priority Queue (complex) | Union-Find (simple) | Kruskal |
| **Debugging** | Moderate difficulty | Easy | Kruskal |
| **Edge Cases** | Handle carefully | Natural handling | Kruskal |
| **Optimization Potential** | Fibonacci Heap (complex) | Already optimal | Kruskal |

### 3.4 Practical Recommendations

**For Academic Projects:**
- Implement both algorithms for learning purposes
- Kruskal is easier to understand and implement correctly
- Prim demonstrates priority queue applications

**For Production Systems:**
- **Use Kruskal's algorithm** as the primary implementation
- Consider Prim only for special cases (adjacency matrix, very small graphs)
- Implement with path compression and union by rank for optimal performance

**For Network Design:**
- Kruskal naturally produces edges in cost order (useful for budgeting)
- Prim builds tree incrementally (useful for staged construction)
- Consider problem constraints beyond just performance

### 3.5 Future Optimizations

**Prim's Algorithm:**
- Implement Fibonacci Heap for O(E + V log V) complexity
- Would narrow but not eliminate performance gap
- Significant implementation complexity increase

**Kruskal's Algorithm:**
- Already near-optimal with Union-Find optimizations
- Parallel sorting could improve large graph performance
- Filtering obviously non-MST edges before sorting

**Hybrid Approaches:**
- Use Kruskal for initial MST
- Use Prim for dynamic updates
- Combine strengths of both algorithms

---

## 4. References

### Primary Sources

1. **Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C.** (2009). *Introduction to Algorithms* (3rd ed.). MIT Press. ISBN: 978-0262033844.
   - Chapter 23: Minimum Spanning Trees
   - Section 23.1: Growing a Minimum Spanning Tree
   - Section 23.2: The Algorithms of Kruskal and Prim

2. **Prim, R. C.** (1957). "Shortest connection networks and some generalizations". *Bell System Technical Journal*, 36(6), 1389-1401.
   - Original paper introducing Prim's algorithm
   - Historical context and motivation

3. **Kruskal, J. B.** (1956). "On the shortest spanning subtree of a graph and the traveling salesman problem". *Proceedings of the American Mathematical Society*, 7(1), 48-50.
   - Original paper describing Kruskal's algorithm
   - Proof of correctness

### Data Structures

4. **Tarjan, R. E.** (1975). "Efficiency of a good but not linear set union algorithm". *Journal of the ACM*, 22(2), 215-225.
   - Union-Find with path compression
   - Analysis of amortized complexity

5. **Fredman, M. L., & Tarjan, R. E.** (1987). "Fibonacci heaps and their uses in improved network optimization algorithms". *Journal of the ACM*, 34(3), 596-615.
   - Advanced priority queue implementation
   - Theoretical improvement for Prim's algorithm

### Supplementary Resources

6. **Sedgewick, R., & Wayne, K.** (2011). *Algorithms* (4th ed.). Addison-Wesley Professional.
   - Practical implementation considerations
   - Java code examples

7. **Skiena, S. S.** (2008). *The Algorithm Design Manual* (2nd ed.). Springer.
   - Chapter 6: Weighted Graph Algorithms
   - Real-world applications and case studies

### Implementation References

8. **Oracle Java Documentation** (2025). *Collections Framework Guide*.
   - https://docs.oracle.com/javase/8/docs/technotes/guides/collections/
   - PriorityQueue implementation details

9. **JUnit 5 Documentation** (2023). *JUnit 5 User Guide*.
   - https://junit.org/junit5/docs/current/user-guide/
   - Testing framework and best practices

### Online Resources

10. **VisuAlgo** (2025). *Minimum Spanning Tree Visualization*.
    - https://visualgo.net/en/mst
    - Interactive algorithm visualization tool
    - Used for algorithm verification

---

## Appendices

### Appendix A: Complete Results Table

All 30 test cases with full metrics available in `mst_comparison_results.csv`.

### Appendix B: Source Code

Complete implementation available at:
- `src/algorithms/PrimMST.java`
- `src/algorithms/KruskalMST.java`
- `src/algorithms/UnionFind.java`

### Appendix C: Test Coverage

- Unit Tests: 10/10 passed (100%)
- Correctness Tests: All MST costs verified
- Performance Tests: All metrics collected
- Edge Cases: Single node, disconnected graphs tested

---

## Acknowledgments

This analysis was conducted as part of the Data Structures and Algorithms course requirements. Special thanks to:
- Course instructor for project guidelines and requirements
- CLRS textbook authors for theoretical foundations
- Open-source Java community for development tools

---

**Report Compiled:** October 2025  
**Total Test Graphs:** 30  
**Total Execution Time:** 46,189 ms (both algorithms combined)  
**Analysis Confidence:** High (100% cost verification, comprehensive test coverage)

---

*This report demonstrates that while both Prim's and Kruskal's algorithms are theoretically equivalent at O(E log V), practical implementation details and operation costs make Kruskal's algorithm significantly superior for real-world applications involving medium to large graphs.*