package graph;

import railwaysystem.MyPath;
import railwaysystem.Railway;

import java.util.Set;

public class UnpleasantGraph extends RailGraph {

    public UnpleasantGraph(Set<MyPath> plist) {
        super(plist, Railway.Us);
    }

    public int writeValue(int thisNode, int prevNode) {
        return Railway.ue(prevNode, thisNode);
    }

    public int getUnpleasantValue(int u, int v) {
        return getValue(u, v);
    }
}
