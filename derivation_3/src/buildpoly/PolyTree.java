package buildpoly;

import factor.Cons;
import factor.Cos;
import factor.Factor;
import factor.Sin;

public class PolyTree {
    private PolyTree leftNode;
    private PolyTree rightNode;

    private String op;
    private Factor factor;

    public void setLeftNode(PolyTree leftNode) {
        this.leftNode = leftNode;
    }

    public PolyTree getLeftNode() {
        return leftNode;
    }

    public void setRightNode(PolyTree rightNode) {
        this.rightNode = rightNode;
    }

    public PolyTree getRightNode() {
        return rightNode;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    public void setFactor(Factor factor) {
        this.factor = factor;
    }

    public Factor getFactor() {
        return factor;
    }

    public boolean isCons() {
        return factor instanceof Cons;
    }

    public boolean isSin() {
        return factor instanceof Sin;
    }

    public boolean isCos() {
        return factor instanceof Cos;
    }
}
