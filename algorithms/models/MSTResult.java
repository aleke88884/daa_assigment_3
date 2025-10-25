package algorithms.models;

public class MSTResult {
    public int graph_id;
    public InputStats input_stats;
    public AlgorithmResult prim;
    public AlgorithmResult kruskal;

    public static class InputStats {
        public int vertices;
        public int edges;

        public InputStats(int v, int e) {
            vertices = v;
            edges = e;
        }
    }

    public MSTResult(int graph_id, InputStats stats, AlgorithmResult prim, AlgorithmResult kruskal) {
        this.graph_id = graph_id;
        this.input_stats = stats;
        this.prim = prim;
        this.kruskal = kruskal;
    }
}