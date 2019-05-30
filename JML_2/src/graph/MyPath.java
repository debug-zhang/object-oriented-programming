package graph;

import com.oocourse.specs2.models.Path;

import java.util.HashMap;
import java.util.Iterator;

public class MyPath implements Path {
    private HashMap<Integer, Integer> nodeList = new HashMap<>();
    private HashMap<Integer, Integer> distinctList = new HashMap<>();
    private int hash = 31;

    public MyPath(int... nodeList) {
        for (int i = 0; i < nodeList.length; i++) {
            this.nodeList.put(i, nodeList[i]);
            this.distinctList.put(nodeList[i], i);
            this.hash += this.hash * nodeList[i];
        }
    }

    @Override
    public int size() {
        return nodeList.size();
    }

    @Override
    public int getNode(int index) {
        return nodeList.get(index);
    }

    @Override
    public boolean containsNode(int node) {
        return distinctList.containsKey(node);
    }

    @Override
    public int getDistinctNodeCount() {
        return distinctList.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyPath && ((MyPath) obj).size() == this.size()
                && ((MyPath) obj).hash == this.hash) {
            for (int i = 0; i < nodeList.size(); i++) {
                if (((MyPath) obj).getNode(i) != this.getNode(i)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean isValid() {
        return nodeList.size() >= 2;
    }

    @Override
    public int compareTo(Path o) {
        MyPath obj = (MyPath) o;

        int size;
        int sizeFlag;
        if (this.size() < obj.size()) {
            size = this.size();
            sizeFlag = -1;
        } else if (this.size() > obj.size()) {
            size = obj.size();
            sizeFlag = 1;
        } else {
            size = this.size();
            sizeFlag = 0;
        }

        for (int i = 0; i < size; i++) {
            if (this.getNode(i) < obj.getNode(i)) {
                return -1;
            } else if (this.getNode(i) > obj.getNode(i)) {
                return 1;
            }
        }

        return sizeFlag;
    }

    @Override
    public Iterator<Integer> iterator() {
        class Iter implements Iterator<Integer> {
            private int cur = 0;

            @Override
            public boolean hasNext() {
                return cur != nodeList.size();
            }

            @Override
            public Integer next() {
                return nodeList.get(cur++);
            }
        }

        return new Iter();
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
