package railwaysystem;

import com.oocourse.specs3.models.Graph;
import com.oocourse.specs3.models.NodeIdNotFoundException;
import com.oocourse.specs3.models.NodeNotConnectedException;
import graph.PriceGraph;
import graph.ShortGraph;
import graph.TransGraph;
import graph.Undigraph;
import graph.UnpleasantGraph;

public class MyGraph extends MyPathContainer implements Graph {
    private Undigraph undigraph;
    private ShortGraph shortGraph;
    private TransGraph transGraph;
    private PriceGraph priceGraph;
    private UnpleasantGraph unpleasantGraph;

    private final int inf = 83647;

    public MyGraph() {
        super();
        undigraph = new Undigraph();
        super.setGraph(undigraph);
        shortGraph = new ShortGraph(getMapping(), undigraph);
        transGraph = new TransGraph(getPlist());
        priceGraph = new PriceGraph(getPlist());
        unpleasantGraph = new UnpleasantGraph(getPlist());
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

        int fromIndex = getMapping().get(fromNodeId);
        int toIndex = getMapping().get(toNodeId);

        return undigraph.getEdge(fromIndex, toIndex) > 0;
    }

    @Override
    public boolean isConnected(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException {

        throwsGraphException(fromNodeId, toNodeId);

        update();

        int fromIndex = getMapping().get(fromNodeId);
        int toIndex = getMapping().get(toNodeId);

        return shortGraph.getLength(fromIndex, toIndex) < inf;
    }

    @Override
    public int getShortestPathLength(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {

        throwsGraphException(fromNodeId, toNodeId);

        update();

        int fromIndex = getMapping().get(fromNodeId);
        int toIndex = getMapping().get(toNodeId);

        if (shortGraph.getLength(fromIndex, toIndex) == inf) {
            throw new NodeNotConnectedException(fromNodeId, toNodeId);
        }

        return shortGraph.getLength(fromIndex, toIndex);
    }

    private void throwsGraphException(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException {

        if (!containsNode(fromNodeId)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!containsNode(toNodeId)) {
            throw new NodeIdNotFoundException(toNodeId);
        }
    }

    public void update() {
        if (super.isModify()) {
            shortGraph.setModify(true);
            transGraph.setModify(true);
            priceGraph.setModify(true);
            unpleasantGraph.setModify(true);
            setModify(false);
        }
    }

    public ShortGraph getShortGraph() {
        return shortGraph;
    }

    public TransGraph getTransGraph() {
        return transGraph;
    }

    public PriceGraph getPriceGraph() {
        return priceGraph;
    }

    public UnpleasantGraph getUnpleasantGraph() {
        return unpleasantGraph;
    }
}
