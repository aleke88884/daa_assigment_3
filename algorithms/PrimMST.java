package algorithms;

import java.util.*;
import algorithms.models.AlgorithmResult;
import algorithms.models.Edge;
import algorithms.models.Graph;

public class PrimMST {

    private static class Node implements Comparable<Node> {
        String vertex;
        double key;

        Node(String v, double k) {
            vertex = v;
            key = k;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.key, o.key);
        }
    }

    public static AlgorithmResult run(Graph graph) {
        long start = System.nanoTime();
        long comparisons = 0, extractMinOps = 0, decreaseKeyOps = 0;

        // Handle empty graph
        if (graph.nodes.isEmpty()) {
            long end = System.nanoTime();
            double timeMs = (end - start) / 1_000_000.0;
            return new AlgorithmResult(new ArrayList<>(), 0.0, 0, timeMs);
        }

        Set<String> inMST = new HashSet<>();
        Map<String, Double> key = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Map<String, Edge> parentEdge = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // Initialize all keys to infinity
        for (String node : graph.nodes) {
            key.put(node, Double.POSITIVE_INFINITY);
            parent.put(node, null);
            parentEdge.put(node, null);
        }

        // Start from the first node
        String startNode = graph.nodes.get(0);
        key.put(startNode, 0.0);
        pq.add(new Node(startNode, 0.0));

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            extractMinOps++;

            // Skip if already processed (duplicate in PQ)
            if (inMST.contains(u.vertex)) {
                continue;
            }

            inMST.add(u.vertex);

            // Explore all adjacent edges
            for (Edge e : graph.adj.get(u.vertex)) {
                String v = e.from.equals(u.vertex) ? e.to : e.from;
                comparisons++;

                // FIX: Ensure key.get(v) is not null and compare properly
                double currentKey = key.getOrDefault(v, Double.POSITIVE_INFINITY);

                // If v is not in MST and edge weight is smaller than current key
                if (!inMST.contains(v) && e.weight < currentKey) {
                    key.put(v, e.weight);
                    parent.put(v, u.vertex);
                    parentEdge.put(v, e);
                    pq.add(new Node(v, e.weight));
                    decreaseKeyOps++;
                }
            }
        }

        // Build MST from parent edges
        List<Edge> mst = new ArrayList<>();
        double total = 0.0;

        for (String node : graph.nodes) {
            Edge e = parentEdge.get(node);
            if (e != null) {
                mst.add(e);
                total += e.weight;
            }
        }

        long end = System.nanoTime();
        long ops = comparisons + extractMinOps + decreaseKeyOps;
        double timeMs = (end - start) / 1_000_000.0;
        return new AlgorithmResult(mst, total, ops, timeMs);
    }
}