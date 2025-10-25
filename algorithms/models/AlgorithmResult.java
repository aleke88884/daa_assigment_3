package algorithms.models;

import java.util.List;

public class AlgorithmResult {
    public final List<Edge> mstEdges;
    public final double totalCost;
    public final long operationCount;
    public final double executionTimeMs;

    public AlgorithmResult(List<Edge> mstEdges, double totalCost,
            long operationCount, double executionTimeMs) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.operationCount = operationCount;
        this.executionTimeMs = executionTimeMs;
    }

    @Override
    public String toString() {
        return String.format("MST[edges=%d, cost=%.2f, ops=%d, time=%.3fms]",
                mstEdges.size(), totalCost, operationCount, executionTimeMs);
    }
}