package algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
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

        Set<String> inMST = new HashSet<>();
        Map<String, Double> key = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (String node : graph.nodes) {
            key.put(node, Double.POSITIVE_INFINITY);
            parent.put(node, null);
        }

        String startNode = graph.nodes.get(0);
        key.put(startNode, 0.0);
        pq.add(new Node(startNode, 0.0));

        while (!pq.isEmpty()) {
            Node u = pq.poll();
            extractMinOps++;
            if (inMST.contains(u.vertex))
                continue;
            inMST.add(u.vertex);

            for (Edge e : graph.adj.get(u.vertex)) {
                String v = e.from.equals(u.vertex) ? e.to : e.from;
                comparisons++;
                if (!inMST.contains(v) && e.weight < key.get(v)) {
                    key.put(v, e.weight);
                    parent.put(v, u.vertex);
                    pq.add(new Node(v, e.weight));
                    decreaseKeyOps++;
                }
            }
        }

        List<Edge> mst = new ArrayList<>();
        double total = 0.0;
        for (String node : graph.nodes) {
            String p = parent.get(node);
            if (p != null) {
                for (Edge e : graph.edges) {
                    if ((e.from.equals(node) && e.to.equals(p)) ||
                            (e.from.equals(p) && e.to.equals(node))) {
                        mst.add(e);
                        total += e.weight;
                        break;
                    }
                }
            }
        }

        long end = System.nanoTime();
        long ops = comparisons + extractMinOps + decreaseKeyOps;
        double timeMs = (end - start) / 1_000_000.0;
        return new AlgorithmResult(mst, total, ops, timeMs);
    }
}
