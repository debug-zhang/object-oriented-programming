package railwaysystem;

import com.oocourse.specs3.models.Path;
import com.oocourse.specs3.models.PathContainer;
import com.oocourse.specs3.models.PathIdNotFoundException;
import com.oocourse.specs3.models.PathNotFoundException;
import graph.Undigraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class MyPathContainer implements PathContainer {
    private HashMap<Integer, MyPath> plist;
    private HashMap<MyPath, Integer> pidlist;
    private HashMap<Integer, Integer> distinctNode;

    private Undigraph graph;
    private HashMap<Integer, Integer> mapping;
    private HashMap<Integer, Integer> reMapping;
    private LinkedList<Integer> removeList;
    private int length;
    private boolean isModify;

    private int id;

    public MyPathContainer() {
        plist = new HashMap<>();
        pidlist = new HashMap<>();
        distinctNode = new HashMap<>();

        mapping = new HashMap<>();
        reMapping = new HashMap<>();
        removeList = new LinkedList<>();
        length = 0;
        isModify = false;

        id = 0;
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

        int thisNode;
        HashMap<Integer, Integer> nodeList = ((MyPath) path).getNodeList();
        for (int i = 0; i < nodeList.size(); i++) {
            thisNode = nodeList.get(i);
            if (distinctNode.containsKey(thisNode)) {
                distinctNode.put(thisNode, distinctNode.get(thisNode) + 1);
                if (i < nodeList.size() - 1) {
                    addMapping(nodeList.get(i + 1));
                    graph.addGraph(mapping.get(nodeList.get(i)),
                            mapping.get(nodeList.get(i + 1)));
                }
            } else {
                distinctNode.put(thisNode, 1);
                addMapping(thisNode);
                if (i < nodeList.size() - 1) {
                    addMapping(nodeList.get(i + 1));
                    graph.addGraph(mapping.get(nodeList.get(i)),
                            mapping.get(nodeList.get(i + 1)));
                }
            }

        }

        id++;
        plist.put(id, (MyPath) path);
        pidlist.put((MyPath) path, id);
        setModify(true);

        return id;
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

    public void setGraph(Undigraph graph) {
        this.graph = graph;
    }

    HashMap<Integer, Integer> getDistinctNode() {
        return distinctNode;
    }

    HashMap<Integer, Integer> getMapping() {
        return mapping;
    }

    HashMap<Integer, Integer> getReMapping() {
        return reMapping;
    }

    Set<MyPath> getPlist() {
        return pidlist.keySet();
    }

    public boolean isModify() {
        return isModify;
    }

    public void setModify(boolean modify) {
        isModify = modify;
    }

    private void addMapping(int nodeId) {
        if (mapping.containsKey(nodeId)) {
            return;
        }

        if (removeList.isEmpty()) {
            mapping.put(nodeId, length);
            reMapping.put(length, nodeId);
            length++;
        } else {
            int index = removeList.poll();
            mapping.put(nodeId, index);
            reMapping.put(index, nodeId);
        }
    }

    private void removeDistinctNode(Path path) {
        int thisNode;
        int index;
        HashMap<Integer, Integer> nodeList = ((MyPath) path).getNodeList();
        for (int i = 0; i < nodeList.size(); i++) {
            thisNode = nodeList.get(i);
            index = mapping.get(nodeList.get(i));
            if (distinctNode.get(thisNode) == 1) {
                distinctNode.remove(thisNode);
                removeList.add(index);
                mapping.remove(thisNode);
                reMapping.remove(index);

                if (i < nodeList.size() - 1) {
                    graph.subGraph(index, mapping.get(nodeList.get(i + 1)));
                }
            } else {
                distinctNode.put(thisNode, distinctNode.get(thisNode) - 1);
                if (i < nodeList.size() - 1) {
                    graph.subGraph(index, mapping.get(nodeList.get(i + 1)));
                }
            }

        }
        setModify(true);
    }

}
