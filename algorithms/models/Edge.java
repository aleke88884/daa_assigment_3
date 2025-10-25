package algorithms.models;

import java.util.Objects;

public class Edge {
    public final String from;
    public final String to;
    public final double weight;

    public Edge(String from, String to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                ((from.equals(edge.from) && to.equals(edge.to)) ||
                        (from.equals(edge.to) && to.equals(edge.from)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(from.hashCode(), to.hashCode()),
                Math.max(from.hashCode(), to.hashCode()),
                weight);
    }

    @Override
    public String toString() {
        return String.format("%s-%s (%.2f)", from, to, weight);
    }
}