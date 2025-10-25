package algorithms.models;

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
    public String toString() {
        return "(" + from + " - " + to + ", " + weight + ")";
    }
}
