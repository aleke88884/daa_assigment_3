package algorithms;

import java.util.*;

import algorithms.models.*;

public class KruskalMST {

    public static AlgorithmResult run(Graph graph) {
        long start = System.nanoTime();
        long[] comparisons = { 0 };

        List<Edge> edges = new ArrayList<>(graph.edges);
        edges.sort((a, b) -> {
            comparisons[0]++;
            return Double.compare(a.weight, b.weight);
        });

        UnionFind uf = new UnionFind();
        for (String node : graph.nodes)
            uf.add(node);

        List<Edge> mst = new ArrayList<>();
        double total = 0.0;

        for (Edge e : edges) {
            if (uf.union(e.from, e.to)) {
                mst.add(e);
                total += e.weight;
            }
        }

        long end = System.nanoTime();
        long ops = comparisons[0] + uf.findOps + uf.unionOps;
        double timeMs = (end - start) / 1_000_000.0;
        return new AlgorithmResult(mst, total, ops, timeMs);
    }
}
