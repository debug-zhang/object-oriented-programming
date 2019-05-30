package graph;

import com.oocourse.specs2.models.Path;
import com.oocourse.specs2.models.PathContainer;
import com.oocourse.specs2.models.PathIdNotFoundException;
import com.oocourse.specs2.models.PathNotFoundException;

import java.util.HashMap;
import java.util.LinkedList;

public class MyPathContainer implements PathContainer {
    private HashMap<Integer, MyPath> plist;
    private HashMap<MyPath, Integer> pidlist;
    private HashMap<Integer, Integer> distinctNode;

    private int[][] graph;
    private HashMap<Integer, Integer> mapping;
    private LinkedList<Integer> removeList;
    private int length;
    private boolean isModify;

    private int id;

    public MyPathContainer() {
        plist = new HashMap<>();
        pidlist = new HashMap<>();
        distinctNode = new HashMap<>();

        graph = new int[260][260];
        mapping = new HashMap<>();
        removeList = new LinkedList<>();
        length = 0;
        isModify = false;

        id = 0;
    }

    HashMap<Integer, Integer> getDistinctNode() {
        return distinctNode;
    }

    int[][] getGraph() {
        return graph;
    }

    HashMap<Integer, Integer> getMapping() {
        return mapping;
    }

    int getLength() {
        return length;
    }

    public boolean isModify() {
        return isModify;
    }

    public void setModify(boolean modify) {
        isModify = modify;
    }

    @Override
    public int size() {
        return plist.size();
    }

    @Override
    public boolean containsPath(Path path) {
        return pidlist.containsKey((MyPath) path);
    }

    @Override
    public boolean containsPathId(int pathId) {
        return plist.containsKey(pathId);
    }

    @Override
    public Path getPathById(int pathId) throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        }

        return plist.get(pathId);
    }

    @Override
    public int getPathId(Path path) throws PathNotFoundException {
        if (path == null || !path.isValid() || !containsPath(path)) {
            throw new PathNotFoundException(path);
        }

        return pidlist.get((MyPath) path);
    }

    @Override
    public int addPath(Path path) {
        if (path == null || !path.isValid()) {
            return 0;
        }

        if (containsPath(path)) {
            try {
                return getPathId(path);
            } catch (PathNotFoundException e) {
                e.printStackTrace();
            }
        }

        int prevId = 0;
        boolean first = true;
        for (int pathId : path) {
            if (first) {
                first = false;
                prevId = pathId;
            }

            if (distinctNode.containsKey(pathId)) {
                distinctNode.put(pathId, distinctNode.get(pathId) + 1);
                renew(mapping.get(prevId), mapping.get(pathId));
            } else {
                distinctNode.put(pathId, 1);
                if (removeList.isEmpty()) {
                    addEdge(prevId, pathId, length);
                    length++;
                } else {
                    int index = removeList.poll();
                    addEdge(prevId, pathId, index);
                }
            }

            prevId = pathId;
        }

        id++;
        plist.put(id, (MyPath) path);
        pidlist.put((MyPath) path, id);
        setModify(true);

        return id;
    }

    private void addEdge(int prevId, int pathId, int length) {
        mapping.put(pathId, length);
        renew(mapping.get(prevId), mapping.get(pathId));
    }

    private void renew(int index1, int index2) {
        graph[index1][index2]++;
        graph[index2][index1]++;
    }

    @Override
    public int removePath(Path path) throws PathNotFoundException {
        if (path == null || !path.isValid() || !containsPath(path)) {
            throw new PathNotFoundException(path);
        }

        int pathId = pidlist.get((MyPath) path);

        plist.remove(pathId);
        pidlist.remove((MyPath) path);

        removeDistinctNode(path);

        return pathId;
    }

    @Override
    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (!containsPathId(pathId)) {
            throw new PathIdNotFoundException(pathId);
        }

        MyPath path = plist.get(pathId);

        plist.remove(pathId);
        pidlist.remove(path);

        removeDistinctNode(path);
    }

    @Override
    public int getDistinctNodeCount() {
        return distinctNode.size();
    }

    private void removeDistinctNode(Path path) {
        int prevId = 0;
        boolean first = true;
        int removeId;
        for (int pathId : path) {
            removeId = mapping.get(pathId);

            if (distinctNode.get(pathId) == 1) {
                distinctNode.remove(pathId);
                removeList.add(removeId);
                mapping.remove(pathId);

                if (!first && graph[removeId][prevId] != 0) {
                    graph[removeId][prevId]--;
                    graph[prevId][removeId]--;
                }
            } else {
                distinctNode.put(pathId, distinctNode.get(pathId) - 1);
                if (!first && graph[removeId][prevId] != 0) {
                    graph[removeId][prevId]--;
                    graph[prevId][removeId]--;
                }
            }
            prevId = removeId;
            first = false;
        }
        setModify(true);
    }
}
