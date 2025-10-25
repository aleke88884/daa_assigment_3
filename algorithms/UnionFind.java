package algorithms;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();
    public long findOps = 0;
    public long unionOps = 0;

    public void add(String x) {
        parent.put(x, x);
        rank.put(x, 0);
    }

    public String find(String x) {
        findOps++;
        if (!parent.get(x).equals(x))
            parent.put(x, find(parent.get(x)));
        return parent.get(x);
    }

    public boolean union(String a, String b) {
        unionOps++;
        String rootA = find(a);
        String rootB = find(b);
        if (rootA.equals(rootB))
            return false;

        int rankA = rank.get(rootA);
        int rankB = rank.get(rootB);
        if (rankA < rankB)
            parent.put(rootA, rootB);
        else if (rankA > rankB)
            parent.put(rootB, rootA);
        else {
            parent.put(rootB, rootA);
            rank.put(rootA, rankA + 1);
        }
        return true;
    }
}
