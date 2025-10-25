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
        this.adj = buildAdjacencyList(edges);
    }

    private Map<String, List<Edge>> buildAdjacencyList(List<Edge> edges) {
        Map<String, List<Edge>> adjList = new HashMap<>();
        for (String node : nodes) {
            adjList.put(node, new ArrayList<>());
        }
        for (Edge e : edges) {
            adjList.get(e.from).add(e);
            adjList.get(e.to).add(e);
        }
        return adjList;
    }

    public boolean isConnected() {
        if (nodes.isEmpty())
            return true;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(nodes.get(0));
        visited.add(nodes.get(0));

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (Edge e : adj.get(current)) {
                String neighbor = e.from.equals(current) ? e.to : e.from;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == nodes.size();
    }
}
