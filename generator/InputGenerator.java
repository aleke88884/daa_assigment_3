package generator;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class InputGenerator {

    private static class Edge {
        String from, to;
        double weight;

        Edge(String from, String to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    private static class Graph {
        int id;
        List<String> nodes;
        List<Edge> edges;

        Graph(int id, List<String> nodes, List<Edge> edges) {
            this.id = id;
            this.nodes = nodes;
            this.edges = edges;
        }
    }

    private static Graph generateGraph(int id, int vertexCount, int edgeTarget) {
        Random rand = new Random();
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++)
            nodes.add("V" + (i + 1));

        Set<String> edgeSet = new HashSet<>();
        List<Edge> edges = new ArrayList<>();

        // Ensure connectedness with a random spanning tree
        for (int i = 1; i < vertexCount; i++) {
            int u = rand.nextInt(i);
            int v = i;
            double w = 1 + rand.nextInt(50);
            edges.add(new Edge("V" + (u + 1), "V" + (v + 1), w));
            edgeSet.add(u + "-" + v);
            edgeSet.add(v + "-" + u);
        }

        // Add extra random edges
        while (edges.size() < edgeTarget) {
            int u = rand.nextInt(vertexCount);
            int v = rand.nextInt(vertexCount);
            if (u == v)
                continue;
            String key = u + "-" + v;
            if (edgeSet.contains(key))
                continue;
            double w = 1 + rand.nextInt(100);
            edges.add(new Edge("V" + (u + 1), "V" + (v + 1), w));
            edgeSet.add(key);
            edgeSet.add(v + "-" + u);
        }

        return new Graph(id, nodes, edges);
    }

    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Graph> graphs = new ArrayList<>();

        graphs.add(generateGraph(1, 5, 10)); // small
        graphs.add(generateGraph(2, 10, 60)); // medium
        graphs.add(generateGraph(3, 20, 200)); // large
        graphs.add(generateGraph(4, 40, 800)); // extra

        JsonObject json = new JsonObject();
        json.add("graphs", gson.toJsonTree(graphs));

        try (FileWriter writer = new FileWriter("assign_3_input.json")) {
            gson.toJson(json, writer);
        }

        System.out.println("✅ assign_3_input.json generated successfully!");
    }
}
