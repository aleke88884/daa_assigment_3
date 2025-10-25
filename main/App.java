package main;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import algorithms.KruskalMST;
import algorithms.PrimMST;
import algorithms.models.AlgorithmResult;
import algorithms.models.Edge;
import algorithms.models.Graph;
import algorithms.models.MSTResult;

public class App {
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // 1. Read input JSON
        Reader reader = new FileReader("ass_3_input.json");
        JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
        JsonArray graphsArray = jsonObj.getAsJsonArray("graphs");

        List<MSTResult> results = new ArrayList<>();

        for (JsonElement el : graphsArray) {
            JsonObject obj = el.getAsJsonObject();
            int id = obj.get("id").getAsInt();
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> nodes = gson.fromJson(obj.get("nodes"), listType);

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

            AlgorithmResult prim = PrimMST.run(g);
            AlgorithmResult kruskal = KruskalMST.run(g);

            MSTResult.InputStats stats = new MSTResult.InputStats(nodes.size(), edges.size());
            results.add(new MSTResult(id, stats, prim, kruskal));
        }

        // 2. Write output JSON
        JsonObject output = new JsonObject();
        output.add("results", gson.toJsonTree(results));
        FileWriter writer = new FileWriter("ass_3_output.json");
        gson.toJson(output, writer);
        writer.close();

        System.out.println("✅ Results written to ass_3_output.json");
    }
}
