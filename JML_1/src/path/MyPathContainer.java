package path;

import com.oocourse.specs1.models.Path;
import com.oocourse.specs1.models.PathContainer;
import com.oocourse.specs1.models.PathIdNotFoundException;
import com.oocourse.specs1.models.PathNotFoundException;

import java.util.HashMap;

public class MyPathContainer implements PathContainer {
    private HashMap<Integer, MyPath> plist;
    private HashMap<MyPath, Integer> pidlist;
    private HashMap<Integer, Integer> distinctNode;
    private int id;

    public MyPathContainer() {
        plist = new HashMap<>();
        pidlist = new HashMap<>();
        distinctNode = new HashMap<>();
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

        for (Integer pathId : path) {
            if (distinctNode.containsKey(pathId)) {
                distinctNode.put(pathId, distinctNode.get(pathId) + 1);
            } else {
                distinctNode.put(pathId, 1);
            }
        }

        id++;
        plist.put(id, (MyPath) path);
        pidlist.put((MyPath) path, id);
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

    private void removeDistinctNode(Path path) {
        for (Integer id : path) {
            if (distinctNode.get(id) == 1) {
                distinctNode.remove(id);
            } else {
                distinctNode.put(id, distinctNode.get(id) - 1);
            }
        }
    }
}
