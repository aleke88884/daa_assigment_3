package generator;

import java.io.FileWriter;
import java.util.ArrayList;

import java.util.List;

import com.google.gson.*;

import algorithms.KruskalMST;
import algorithms.PrimMST;
import algorithms.models.AlgorithmResult;
import algorithms.models.Edge;
import algorithms.models.Graph;

import java.io.*;

public class CSVResultsGenerator {

    public static void main(String[] args) throws IOException {
        generateComparisonCSV();
    }

    public static void generateComparisonCSV() throws IOException {
        Gson gson = new Gson();

        // Read input JSON
        Reader reader = new FileReader("datasets/assign_3_input.json");
        JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
        JsonArray graphsArray = jsonObj.getAsJsonArray("graphs");

        // Prepare CSV output
        FileWriter csvWriter = new FileWriter("mst_comparison_results.csv");

        // Write header
        csvWriter.append("Graph_ID,Vertices,Edges,Density,");
        csvWriter.append("Prim_Cost,Prim_Edges,Prim_Operations,Prim_Time_ms,");
        csvWriter.append("Kruskal_Cost,Kruskal_Edges,Kruskal_Operations,Kruskal_Time_ms,");
        csvWriter.append("Cost_Match,Time_Difference_ms,Operation_Difference,Kruskal_Faster\n");

        // Process each graph
        for (JsonElement el : graphsArray) {
            JsonObject obj = el.getAsJsonObject();
            int id = obj.get("id").getAsInt();

            // Parse nodes
            List<String> nodes = new ArrayList<>();
            JsonArray nodesArray = obj.getAsJsonArray("nodes");
            for (JsonElement n : nodesArray) {
                nodes.add(n.getAsString());
            }

            // Parse edges
            List<Edge> edges = new ArrayList<>();
            JsonArray edgesJson = obj.getAsJsonArray("edges");
            for (JsonElement e : edgesJson) {
                JsonObject eo = e.getAsJsonObject();
                edges.add(new Edge(
                        eo.get("from").getAsString(),
                        eo.get("to").getAsString(),
                        eo.get("weight").getAsDouble()));
            }

            Graph g = new Graph(id, nodes, edges);

            // Calculate density
            int V = nodes.size();
            int E = edges.size();
            double maxEdges = (V * (V - 1)) / 2.0;
            double density = maxEdges > 0 ? E / maxEdges : 0;

            // Run algorithms
            AlgorithmResult prim = PrimMST.run(g);
            AlgorithmResult kruskal = KruskalMST.run(g);

            // Write row
            csvWriter.append(String.format("%d,%d,%d,%.2f,", id, V, E, density));
            csvWriter.append(String.format("%.2f,%d,%d,%.3f,",
                    prim.totalCost, prim.mstEdges.size(),
                    prim.operationCount, prim.executionTimeMs));
            csvWriter.append(String.format("%.2f,%d,%d,%.3f,",
                    kruskal.totalCost, kruskal.mstEdges.size(),
                    kruskal.operationCount, kruskal.executionTimeMs));

            boolean costMatch = Math.abs(prim.totalCost - kruskal.totalCost) < 0.01;
            double timeDiff = prim.executionTimeMs - kruskal.executionTimeMs;
            long opDiff = prim.operationCount - kruskal.operationCount;
            boolean kruskalFaster = kruskal.executionTimeMs < prim.executionTimeMs;

            csvWriter.append(String.format("%s,%.3f,%d,%s\n",
                    costMatch, timeDiff, opDiff, kruskalFaster));
        }

        csvWriter.close();
        reader.close();

        System.out.println("✅ CSV comparison file generated: mst_comparison_results.csv");
    }
}