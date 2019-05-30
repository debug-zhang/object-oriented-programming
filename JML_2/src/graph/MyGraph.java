package graph;

import com.oocourse.specs2.models.Graph;
import com.oocourse.specs2.models.NodeIdNotFoundException;
import com.oocourse.specs2.models.NodeNotConnectedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MyGraph extends MyPathContainer implements Graph {
    private int[][] graph;
    private HashMap<Integer, Integer> mapping;
    private int[][] renewGraph;
    private HashSet<String> renewList;
    private int inf = 83647;

    public MyGraph() {
        super();

        graph = super.getGraph();
        mapping = super.getMapping();

        renewList = new HashSet<>();
        renewGraph = new int[260][260];
    }

    @Override
    public boolean containsNode(int nodeId) {
        return super.getDistinctNode().containsKey(nodeId);
    }

    @Override
    public boolean containsEdge(int fromNodeId, int toNodeId) {
        if (!containsNode(fromNodeId) || !containsNode(toNodeId)) {
            return false;
        }
        return graph[mapping.get(fromNodeId)][mapping.get(toNodeId)] > 0;
    }

    @Override
    public boolean isConnected(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException {

        cache(fromNodeId, toNodeId);
        if (fromNodeId == toNodeId) { return true; }
        return renewGraph[mapping.get(fromNodeId)][mapping.get(toNodeId)] < inf;
    }

    @Override
    public int getShortestPathLength(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {

        cache(fromNodeId, toNodeId);

        if (fromNodeId == toNodeId) { return 0; }
        if (renewGraph[mapping.get(fromNodeId)][mapping.get(toNodeId)] == inf) {
            throw new NodeNotConnectedException(fromNodeId, toNodeId);
        }

        return renewGraph[mapping.get(fromNodeId)][mapping.get(toNodeId)];
    }

    private void cache(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException {
        if (!containsNode(fromNodeId)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!containsNode(toNodeId)) {
            throw new NodeIdNotFoundException(toNodeId);
        }

        if (super.isModify()) {
            renewList.clear();
            for (int i = 0; i < super.getLength(); i++) {
                for (int j = 0; j < super.getLength(); j++) {
                    if (i == j) {
                        renewGraph[i][j] = 0;
                    } else if (graph[i][j] == 0) {
                        renewGraph[i][j] = inf;
                    } else {
                        renewGraph[i][j] = 1;
                    }
                }
            }
            setModify(false);
        }

        if (renewGraph[mapping.get(fromNodeId)][mapping.get(toNodeId)] == 1
                || renewList.contains(mapping.get(fromNodeId)
                + "_" + mapping.get(toNodeId))) {
            return;
        }

        bfs(mapping.get(fromNodeId), mapping.get(toNodeId));
    }

    private void bfs(int fromNodeId, int toNodeId) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[260];
        HashMap<Integer, Integer> pathLength = new HashMap<>();

        visited[fromNodeId] = true;
        pathLength.put(fromNodeId, 0);
        queue.offer(fromNodeId);

        while (!queue.isEmpty()) {
            int index = queue.poll();
            for (int i = 0; i < super.getLength(); i++) {
                if (renewGraph[index][i] == 1 && !visited[i]) {
                    pathLength.put(i, pathLength.get(index) + 1);
                    queue.offer(i);
                    visited[i] = true;

                    if (fromNodeId != i) {
                        renewGraph[fromNodeId][i] = pathLength.get(i);
                        renewGraph[i][fromNodeId] = pathLength.get(i);
                        renewList.add(fromNodeId + "_" + i);
                        renewList.add(i + "_" + fromNodeId);
                    }
                }

                if (index == toNodeId) {
                    return;
                }

            }
        }
    }
}
