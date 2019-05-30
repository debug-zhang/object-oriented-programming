package graph;

import javafx.util.Pair;
import railwaysystem.MyPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class RailGraph {
    private Set<MyPath> plist;
    private HashMap<String, Integer> nodeMap;
    private int weight;
    private HashMap<Integer, ArrayList<Integer>> node2index;
    private ArrayList<ArrayList<Pair<Integer, Integer>>>
            edgeList;
    private HashMap<Integer, HashMap<Integer, Integer>>
            resultMap;

    private int pathId = 1;
    private int nodeNum = 0;
    private boolean modify = false;
    private final int size = 4000;
    private final int inf = 83647;

    public RailGraph(Set<MyPath> plist, int weight) {
        this.plist = plist;
        this.weight = weight;

        this.nodeMap = new HashMap<>();
        this.node2index = new HashMap<>();
        this.edgeList = new ArrayList<>();
        this.resultMap = new HashMap<>();
    }

    public void rebuild() {
        nodeMap.clear();
        node2index.clear();
        edgeList.clear();
        resultMap.clear();
        pathId = 1;
        nodeNum = 0;
        int u;
        int v;
        int thisNode;
        int prevNode = 0;
        edgeList.add(nodeNum, new ArrayList<>());
        for (MyPath path : plist) {
            prevNode = 0;
            for (int i = 0; i < path.size(); i++) {
                thisNode = path.getNode(i);
                if (!nodeMap.containsKey(thisNode + "_" + pathId)) {
                    nodeNum++;
                    nodeMap.put(thisNode + "_" + pathId, nodeNum);
                    edgeList.add(nodeNum, new ArrayList<>());
                    putNodeIndex(thisNode, nodeNum);
                }

                for (int j = 1; j < pathId; j++) {
                    if (nodeMap.containsKey(thisNode + "_" + j)) {
                        u = nodeMap.get(thisNode + "_" + pathId);
                        v = nodeMap.get(thisNode + "_" + j);
                        setEdge(u, v, weight);
                    }
                }

                if (i != 0) {
                    u = nodeMap.get(thisNode + "_" + pathId);
                    v = nodeMap.get(prevNode + "_" + pathId);
                    setEdge(u, v, writeValue(thisNode, prevNode));
                }
                prevNode = thisNode;
            }
            pathId++;
        }
    }

    public int writeValue(int u, int v) {
        return 1;
    }

    public int getValue(int u, int v) {
        if (isModify()) {
            rebuild();
            setModify(false);
        }

        if (u == v) {
            return 0;
        } else if (resultMap.containsKey(u)) {
            return resultMap.get(u).getOrDefault(v, inf);
        } else if (resultMap.containsKey(v)) {
            return resultMap.get(v).getOrDefault(u, inf);
        } else {
            dijkstra(u);
            return resultMap.get(u).getOrDefault(v, inf);
        }
    }

    public void dijkstra(int node) {
        HashMap<Integer, Integer> tempMap = new HashMap<>();
        boolean[] visit = new boolean[nodeNum + 1];
        int[] dist = new int[nodeNum + 1];

        for (int i = 1; i <= nodeNum; i++) {
            dist[i] = inf;
        }

        for (int temp : node2index.get(node)) {
            visit[temp] = true;
            tempMap.put(temp, 0);
            for (Pair<Integer, Integer> pair : edgeList.get(temp)) {
                if (pair.getValue() < dist[pair.getKey()]) {
                    dist[pair.getKey()] = pair.getValue();
                }
            }
        }

        for (int i = 1; i <= nodeNum - node2index.get(node).size(); i++) {
            int nearestNode = 0;
            int minLength = inf;

            for (int temp = 1; temp <= nodeNum; temp++) {
                if (!visit[temp] && dist[temp] < minLength) {
                    minLength = dist[temp];
                    nearestNode = temp;
                }
            }

            visit[nearestNode] = true;
            tempMap.put(nearestNode, minLength);

            for (Pair<Integer, Integer> pairTmp : edgeList.get(nearestNode)) {
                int thisIndex = pairTmp.getKey();
                int distTmp = minLength + pairTmp.getValue();
                if (distTmp < dist[thisIndex]) {
                    dist[thisIndex] = distTmp;
                }
            }
        }
        resultMap.put(node, new HashMap<>());
        for (int toNode : node2index.keySet()) {
            ArrayList<Integer> indexList = node2index.get(toNode);
            int minLenth = inf;
            for (int indexTmp : indexList) {
                if (tempMap.containsKey(indexTmp)) {
                    int thisLenth = tempMap.get(indexTmp);
                    if (thisLenth < minLenth) {
                        minLenth = thisLenth;
                    }
                }
            }
            if (minLenth < inf) {
                resultMap.get(node).put(toNode, minLenth);
            }
        }
    }

    public void setEdge(int u, int v, int value) {
        edgeList.get(u).add(new Pair<>(v, value));
        edgeList.get(v).add(new Pair<>(u, value));
    }

    private void putNodeIndex(int node, int index) {
        node2index.computeIfAbsent(node, k -> new ArrayList<>());
        node2index.get(node).add(index);
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public boolean isModify() {
        return modify;
    }

    public int getWeight() {
        return weight;
    }

    public int getInf() {
        return inf;
    }

    public int getPathId() {
        return pathId;
    }

    public HashMap<String, Integer> getNodeMap() {
        return nodeMap;
    }
}
