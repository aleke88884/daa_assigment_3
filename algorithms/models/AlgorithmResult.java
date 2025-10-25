package algorithms.models;

import java.util.List;

public class AlgorithmResult {
    public List<Edge> mst_edges;
    public double total_cost;
    public long operations_count;
    public double execution_time_ms;

    public AlgorithmResult(List<Edge> mst_edges, double total_cost, long operations_count, double execution_time_ms) {
        this.mst_edges = mst_edges;
        this.total_cost = total_cost;
        this.operations_count = operations_count;
        this.execution_time_ms = execution_time_ms;
    }
}
