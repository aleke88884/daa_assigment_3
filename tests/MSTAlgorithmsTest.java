package tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import algorithms.KruskalMST;
import algorithms.PrimMST;
import algorithms.models.AlgorithmResult;
import algorithms.models.Edge;
import algorithms.models.Graph;

import java.util.*;

/**
 * Comprehensive JUnit test suite for MST algorithms
 * Tests correctness, performance, and edge cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MSTAlgorithmsTest {

    private static final double EPSILON = 0.01; // For floating-point comparisons

    // ========================================
    // CORRECTNESS TESTS
    // ========================================

    @Test
    @Order(1)
    @DisplayName("Test 1: Identical MST Cost - Small Graph")
    public void testIdenticalCostSmallGraph() {
        // Graph: 5 vertices, simple triangle + extensions
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("A", "C", 4.0),
                new Edge("B", "C", 2.0),
                new Edge("B", "D", 5.0),
                new Edge("C", "D", 3.0),
                new Edge("D", "E", 6.0));
        Graph graph = new Graph(1, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(prim.totalCost, kruskal.totalCost, EPSILON,
                "MST costs must be identical");
    }

    @Test
    @Order(2)
    @DisplayName("Test 2: Identical MST Cost - Medium Graph")
    public void testIdenticalCostMediumGraph() {
        // Generate 10-vertex complete graph
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nodes.add("V" + i);
        }

        List<Edge> edges = new ArrayList<>();
        Random rand = new Random(42); // Fixed seed for reproducibility
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                edges.add(new Edge("V" + i, "V" + j, rand.nextInt(100) + 1));
            }
        }

        Graph graph = new Graph(2, nodes, edges);
        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(prim.totalCost, kruskal.totalCost, EPSILON,
                "MST costs must match on medium graphs");
    }

    @Test
    @Order(3)
    @DisplayName("Test 3: MST Edge Count = V - 1")
    public void testEdgeCountEqualsVMinusOne() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("B", "C", 2.0),
                new Edge("C", "D", 3.0),
                new Edge("A", "D", 4.0),
                new Edge("B", "D", 5.0));
        Graph graph = new Graph(3, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        int expectedEdges = nodes.size() - 1;
        assertEquals(expectedEdges, prim.mstEdges.size(),
                "Prim's MST must have V-1 edges");
        assertEquals(expectedEdges, kruskal.mstEdges.size(),
                "Kruskal's MST must have V-1 edges");
    }

    @Test
    @Order(4)
    @DisplayName("Test 4: MST is Acyclic")
    public void testMSTIsAcyclic() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("B", "C", 2.0),
                new Edge("C", "D", 3.0),
                new Edge("D", "A", 4.0));
        Graph graph = new Graph(4, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertFalse(hasCycle(prim.mstEdges, nodes),
                "Prim's MST must be acyclic");
        assertFalse(hasCycle(kruskal.mstEdges, nodes),
                "Kruskal's MST must be acyclic");
    }

    @Test
    @Order(5)
    @DisplayName("Test 5: MST Connects All Vertices")
    public void testMSTConnectsAllVertices() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("B", "C", 2.0),
                new Edge("C", "D", 3.0),
                new Edge("D", "E", 4.0),
                new Edge("A", "E", 10.0));
        Graph graph = new Graph(5, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertTrue(isConnected(prim.mstEdges, nodes),
                "Prim's MST must connect all vertices");
        assertTrue(isConnected(kruskal.mstEdges, nodes),
                "Kruskal's MST must connect all vertices");
    }

    @Test
    @Order(6)
    @DisplayName("Test 6: Single Vertex Graph")
    public void testSingleVertexGraph() {
        List<String> nodes = Arrays.asList("A");
        List<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(6, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(0.0, prim.totalCost, EPSILON, "Single vertex has cost 0");
        assertEquals(0.0, kruskal.totalCost, EPSILON, "Single vertex has cost 0");
        assertEquals(0, prim.mstEdges.size(), "Single vertex has no edges");
        assertEquals(0, kruskal.mstEdges.size(), "Single vertex has no edges");
    }

    @Test
    @Order(7)
    @DisplayName("Test 7: Empty Graph")
    public void testEmptyGraph() {
        List<String> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Graph graph = new Graph(7, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(0.0, prim.totalCost, EPSILON, "Empty graph has cost 0");
        assertEquals(0.0, kruskal.totalCost, EPSILON, "Empty graph has cost 0");
    }

    // ========================================
    // PERFORMANCE TESTS
    // ========================================

    @Test
    @Order(8)
    @DisplayName("Test 8: Execution Time Non-Negative")
    public void testExecutionTimeNonNegative() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("B", "C", 2.0),
                new Edge("C", "D", 3.0),
                new Edge("D", "E", 4.0));
        Graph graph = new Graph(8, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertTrue(prim.executionTimeMs >= 0,
                "Prim's execution time must be non-negative");
        assertTrue(kruskal.executionTimeMs >= 0,
                "Kruskal's execution time must be non-negative");
    }

    @Test
    @Order(9)
    @DisplayName("Test 9: Operation Count Non-Negative")
    public void testOperationCountNonNegative() {
        List<String> nodes = Arrays.asList("A", "B", "C");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("B", "C", 2.0));
        Graph graph = new Graph(9, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertTrue(prim.operationCount >= 0,
                "Prim's operation count must be non-negative");
        assertTrue(kruskal.operationCount >= 0,
                "Kruskal's operation count must be non-negative");
    }

    @Test
    @Order(10)
    @DisplayName("Test 10: Results Reproducible")
    public void testResultsReproducible() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 5.0),
                new Edge("B", "C", 3.0),
                new Edge("C", "D", 2.0),
                new Edge("A", "D", 7.0));
        Graph graph = new Graph(10, nodes, edges);

        // Run multiple times
        AlgorithmResult prim1 = PrimMST.run(graph);
        AlgorithmResult prim2 = PrimMST.run(graph);
        AlgorithmResult kruskal1 = KruskalMST.run(graph);
        AlgorithmResult kruskal2 = KruskalMST.run(graph);

        assertEquals(prim1.totalCost, prim2.totalCost, EPSILON,
                "Prim's results must be reproducible");
        assertEquals(kruskal1.totalCost, kruskal2.totalCost, EPSILON,
                "Kruskal's results must be reproducible");
    }

    // ========================================
    // EDGE CASE TESTS
    // ========================================

    @Test
    @Order(11)
    @DisplayName("Test 11: All Equal Weights")
    public void testAllEqualWeights() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 5.0),
                new Edge("B", "C", 5.0),
                new Edge("C", "D", 5.0),
                new Edge("A", "D", 5.0),
                new Edge("B", "D", 5.0),
                new Edge("A", "C", 5.0));
        Graph graph = new Graph(11, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(prim.totalCost, kruskal.totalCost, EPSILON,
                "Equal weights: costs must match");
        assertEquals(15.0, prim.totalCost, EPSILON,
                "Equal weights: total cost = 3 * 5");
    }

    @Test
    @Order(12)
    @DisplayName("Test 12: Large Weight Range")
    public void testLargeWeightRange() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("B", "C", 1000.0),
                new Edge("C", "D", 1.0),
                new Edge("A", "D", 2.0));
        Graph graph = new Graph(12, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(prim.totalCost, kruskal.totalCost, EPSILON,
                "Large weight range: costs must match");
    }

    @Test
    @Order(13)
    @DisplayName("Test 13: Dense Graph")
    public void testDenseGraph() {
        // Complete graph with 6 vertices (15 edges)
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E", "F");
        List<Edge> edges = new ArrayList<>();
        int weight = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                edges.add(new Edge(
                        nodes.get(i),
                        nodes.get(j),
                        weight++));
            }
        }
        Graph graph = new Graph(13, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(prim.totalCost, kruskal.totalCost, EPSILON,
                "Dense graph: costs must match");
        assertEquals(5, prim.mstEdges.size(),
                "Dense graph: MST has V-1 edges");
    }

    @Test
    @Order(14)
    @DisplayName("Test 14: Sparse Graph")
    public void testSparseGraph() {
        // Linear chain
        List<String> nodes = Arrays.asList("A", "B", "C", "D", "E");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("B", "C", 2.0),
                new Edge("C", "D", 3.0),
                new Edge("D", "E", 4.0));
        Graph graph = new Graph(14, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        assertEquals(10.0, prim.totalCost, EPSILON,
                "Sparse graph: total cost = 1+2+3+4");
        assertEquals(prim.totalCost, kruskal.totalCost, EPSILON,
                "Sparse graph: costs must match");
    }

    @Test
    @Order(15)
    @DisplayName("Test 15: Multiple Components (Disconnected)")
    public void testDisconnectedGraph() {
        // Two separate components
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = Arrays.asList(
                new Edge("A", "B", 1.0),
                new Edge("C", "D", 2.0)
        // A-B and C-D are disconnected
        );
        Graph graph = new Graph(15, nodes, edges);

        AlgorithmResult prim = PrimMST.run(graph);
        AlgorithmResult kruskal = KruskalMST.run(graph);

        // MST of disconnected graph should only include edges from
        // the component containing the start vertex
        assertTrue(prim.mstEdges.size() < nodes.size() - 1,
                "Disconnected graph: Prim's MST incomplete");
        assertTrue(kruskal.mstEdges.size() < nodes.size() - 1,
                "Disconnected graph: Kruskal's MST incomplete");
    }

    // ========================================
    // SCALABILITY TESTS
    // ========================================

    @Test
    @Order(16)
    @DisplayName("Test 16: Large Graph Performance")
    public void testLargeGraphPerformance() {
        // Generate 100-vertex graph
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            nodes.add("V" + i);
        }

        List<Edge> edges = new ArrayList<>();
        Random rand = new Random(123);

        // Create sparse graph (about 300 edges)
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < Math.min(i + 4, 100); j++) {
                edges.add(new Edge("V" + i, "V" + j, rand.nextInt(100) + 1));
            }
        }

        Graph graph = new Graph(16, nodes, edges);

        long startPrim = System.nanoTime();
        AlgorithmResult prim = PrimMST.run(graph);
        long endPrim = System.nanoTime();

        long startKruskal = System.nanoTime();
        AlgorithmResult kruskal = KruskalMST.run(graph);
        long endKruskal = System.nanoTime();

        double primTime = (endPrim - startPrim) / 1_000_000.0;
        double kruskalTime = (endKruskal - startKruskal) / 1_000_000.0;

        System.out.printf("Large Graph (100V): Prim=%.2fms, Kruskal=%.2fms%n",
                primTime, kruskalTime);

        assertEquals(prim.totalCost, kruskal.totalCost, EPSILON,
                "Large graph: costs must match");
        assertTrue(primTime < 1000, "Prim should complete in < 1 second");
        assertTrue(kruskalTime < 1000, "Kruskal should complete in < 1 second");
    }

    // ========================================
    // HELPER METHODS
    // ========================================

    /**
     * Check if MST contains a cycle using DFS
     */
    private boolean hasCycle(List<Edge> mstEdges, List<String> nodes) {
        Map<String, List<String>> adj = new HashMap<>();
        for (String node : nodes) {
            adj.put(node, new ArrayList<>());
        }

        for (Edge e : mstEdges) {
            adj.get(e.from).add(e.to);
            adj.get(e.to).add(e.from);
        }

        Set<String> visited = new HashSet<>();
        return hasCycleDFS(nodes.get(0), null, adj, visited);
    }

    private boolean hasCycleDFS(String node, String parent,
            Map<String, List<String>> adj,
            Set<String> visited) {
        visited.add(node);

        for (String neighbor : adj.get(node)) {
            if (!visited.contains(neighbor)) {
                if (hasCycleDFS(neighbor, node, adj, visited)) {
                    return true;
                }
            } else if (!neighbor.equals(parent)) {
                return true; // Back edge found = cycle
            }
        }
        return false;
    }

    /**
     * Check if MST connects all vertices using BFS
     */
    private boolean isConnected(List<Edge> mstEdges, List<String> nodes) {
        if (nodes.isEmpty())
            return true;
        if (mstEdges.isEmpty() && nodes.size() > 1)
            return false;

        Map<String, List<String>> adj = new HashMap<>();
        for (String node : nodes) {
            adj.put(node, new ArrayList<>());
        }

        for (Edge e : mstEdges) {
            adj.get(e.from).add(e.to);
            adj.get(e.to).add(e.from);
        }

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(nodes.get(0));
        visited.add(nodes.get(0));

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : adj.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == nodes.size();
    }
}