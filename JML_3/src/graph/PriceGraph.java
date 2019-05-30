package graph;

import railwaysystem.MyPath;
import railwaysystem.Railway;

import java.util.Set;

public class PriceGraph extends RailGraph {

    public PriceGraph(Set<MyPath> plist) {
        super(plist, Railway.price);
    }

    public int getPrice(int u, int v) {
        return getValue(u, v);
    }
}
