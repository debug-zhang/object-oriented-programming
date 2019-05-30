package graph;

import railwaysystem.MyPath;
import railwaysystem.Railway;

import java.util.Set;

public class TransGraph extends RailGraph {

    public TransGraph(Set<MyPath> plist) {
        super(plist, Railway.trans);
    }

    public int getTransCount(int u, int v) {
        return getValue(u, v) / getWeight();
    }
}
