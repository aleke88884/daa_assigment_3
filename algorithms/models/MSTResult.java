package algorithms.models;

public class MSTResult {
    public final int graphId;
    public final InputStats inputStats;
    public final AlgorithmResult prim;
    public final AlgorithmResult kruskal;

    public MSTResult(int graphId, InputStats inputStats,
            AlgorithmResult prim, AlgorithmResult kruskal) {
        this.graphId = graphId;
        this.inputStats = inputStats;
        this.prim = prim;
        this.kruskal = kruskal;
    }

    public static class InputStats {
        public final int vertexCount;
        public final int edgeCount;

        public InputStats(int vertexCount, int edgeCount) {
            this.vertexCount = vertexCount;
            this.edgeCount = edgeCount;
        }
    }

    @Override
    public String toString() {
        return String.format("Graph %d: V=%d, E=%d\n  Prim: %s\n  Kruskal: %s",
                graphId, inputStats.vertexCount, inputStats.edgeCount,
                prim, kruskal);
    }
}