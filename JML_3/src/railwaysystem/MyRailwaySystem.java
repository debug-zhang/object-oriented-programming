package railwaysystem;

import com.oocourse.specs3.models.NodeIdNotFoundException;
import com.oocourse.specs3.models.NodeNotConnectedException;
import com.oocourse.specs3.models.Path;
import com.oocourse.specs3.models.RailwaySystem;

public class MyRailwaySystem extends MyGraph implements RailwaySystem {

    public MyRailwaySystem() {
        super();
    }

    @Override
    public int getLeastTicketPrice(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {

        throwsRailException(fromNodeId, toNodeId);
        update();
        return getPriceGraph().getPrice(fromNodeId, toNodeId);
    }

    @Override
    public int getLeastTransferCount(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {

        throwsRailException(fromNodeId, toNodeId);
        update();
        return getTransGraph().getTransCount(fromNodeId, toNodeId);
    }

    @Override
    public int getLeastUnpleasantValue(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {

        throwsRailException(fromNodeId, toNodeId);
        update();
        return getUnpleasantGraph().getUnpleasantValue(fromNodeId, toNodeId);
    }

    @Override
    public int getConnectedBlockCount() {
        update();
        return getShortGraph().getBlockCount();
    }

    @Override
    public int getUnpleasantValue(Path path, int fromIndex, int toIndex) {
        return 0;
    }

    private void throwsRailException(int fromNodeId, int toNodeId)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        if (!containsNode(fromNodeId)) {
            throw new NodeIdNotFoundException(fromNodeId);
        } else if (!containsNode(toNodeId)) {
            throw new NodeIdNotFoundException(toNodeId);
        } else if (!isConnected(fromNodeId, toNodeId)) {
            throw new NodeNotConnectedException(fromNodeId, toNodeId);
        }
    }
}
