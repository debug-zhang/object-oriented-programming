package graph;

public class Undigraph {
    private int[][] graph;
    private final int size = 150;

    public Undigraph() {
        this.graph = new int[size][size];
    }

    public void addGraph(int u, int v) {
        if (u == v) {
            graph[u][v]++;
        } else {
            graph[u][v]++;
            graph[v][u]++;
        }
    }

    public void subGraph(int u, int v) {
        if (u == v) {
            graph[u][v]--;
        } else {
            graph[u][v]--;
            graph[v][u]--;
        }
    }

    public int getEdge(int u, int v) {
        return graph[u][v];
    }

    public int[][] getGraph() {
        return graph;
    }
}
