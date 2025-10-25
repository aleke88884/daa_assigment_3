package tests;

import algorithms.*;
import algorithms.models.AlgorithmResult;
import algorithms.models.Edge;
import algorithms.models.Graph;
import algorithms.models.MSTResult;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MSTAlgorithmsTest {

    static List<Graph> graphs;

    @BeforeAll
    static void loadGraphs() throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader("assign_3_input.json");
        JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
        JsonArray arr = jsonObj.getAsJsonArray("graphs");

        graphs = new ArrayList<>();
        for (JsonElement el : arr) {
            JsonObject obj = el.getAsJsonObject();
            int id = obj.get("id").getAsInt();
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> nodes = gson.fromJson(obj.get("nodes"), listType);

            List<Edge> edges = new ArrayList<>();
            for (JsonElement e : obj.getAsJsonArray("edges")) {
                JsonObject eo = e.getAsJsonObject();
                edges.add(new Edge(
                        eo.get("from").getAsString(),
                        eo.get("to").getAsString(),
                        eo.get("weight").getAsDouble()));
            }
            graphs.add(new Graph(id, nodes, edges));
        }
        reader.close();
    }

    @Test
    @Order(1)
    void testAlgorithmsAndWriteOutput() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<MSTResult> results = new ArrayList<>();

        for (Graph g : graphs) {
            AlgorithmResult prim = PrimMST.run(g);
            AlgorithmResult kruskal = KruskalMST.run(g);

            // ✅ correctness checks
            Assertions.assertEquals(prim.total_cost, kruskal.total_cost, 1e-6,
                    "MST total cost must match");
            Assertions.assertEquals(g.nodes.size() - 1, prim.mst_edges.size(),
                    "MST must have V-1 edges");

            MSTResult.InputStats stats = new MSTResult.InputStats(g.nodes.size(), g.edges.size());
            results.add(new MSTResult(g.id, stats, prim, kruskal));
        }

        JsonObject output = new JsonObject();
        output.add("results", gson.toJsonTree(results));
        FileWriter writer = new FileWriter("assign_3_output.json");
        gson.toJson(output, writer);
        writer.close();

        System.out.println("✅ MST tests passed & output.json generated");
    }
}