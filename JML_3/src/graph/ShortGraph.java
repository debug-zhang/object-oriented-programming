package graph;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ShortGraph {
    private int[][] graph;
    private HashMap<Integer, HashMap<Integer, Integer>> cache;
    private HashMap<Integer, Integer> mapping;
    private Undigraph undigraph;
    private int blockCount;
    private boolean blockFlag;
    private boolean modify;
    private final int size = 150;
    private final int inf = 83647;

    public ShortGraph(HashMap<Integer, Integer> mapping, Undigraph undigraph) {
        this.graph = new int[size][size];
        this.cache = new HashMap<>();
        this.mapping = mapping;
        this.undigraph = undigraph;
        this.blockCount = 0;
        this.blockFlag = false;
        this.modify = false;
    }

    public void rebuild() {
        graph = undigraph.getGraph();
        cache.clear();
        blockCount = 0;
        blockFlag = false;
    }

    public int getLength(int from, int to) {
        if (modify) {
            rebuild();
            modify = false;
        }

        if (from == to) {
            return 0;
        } else if (cache.containsKey(from)) {
            return cache.get(from).getOrDefault(to, inf);
        } else if (cache.containsKey(to)) {
            return cache.get(to).getOrDefault(from, inf);
        }

        cache.put(from, new HashMap<>());
        boolean[] visit = new boolean[size];
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

        visit[from] = true;
        queue.offer(new Pair<>(from, 0));

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> node = queue.poll();
            cache.get(from).put(node.getKey(), node.getValue());

            for (int i = 0; i < size; i++) {
                if (graph[node.getKey()][i] > 0 && !visit[i]) {
                    queue.offer(new Pair<>(i, node.getValue() + 1));
                    visit[i] = true;
                }
            }
        }

        return cache.get(from).getOrDefault(to, inf);
    }

    public int getBlockCount() {
        if (modify) {
            rebuild();
            setModify(false);
        }

        if (blockFlag) {
            return blockCount;
        }

        boolean[] flag = new boolean[size];
        for (int i = 0; i < size; i++) {
            if (flag[i] || !mapping.containsValue(i)) {
                continue;
            }
            for (int j = i; j < size; j++) {
                if (mapping.containsValue(j) && getLength(i, j) < inf) {
                    flag[j] = true;
                }
            }
            blockCount++;
        }

        blockFlag = true;
        return blockCount;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }
}
