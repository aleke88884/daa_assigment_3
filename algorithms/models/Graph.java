package algorithms.models;

import java.util.*;

public class Graph {
    public final int id;
    public final List<String> nodes;
    public final List<Edge> edges;
    public final Map<String, List<Edge>> adj;

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
        this.adj = new HashMap<>();

        for (String node : nodes)
            adj.put(node, new ArrayList<>());
        for (Edge e : edges) {
            adj.get(e.from).add(e);
            adj.get(e.to).add(e);
        }
    }
}
