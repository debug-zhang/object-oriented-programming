package computepoly;

import buildpoly.PolyTree;
import buildpoly.Operate;

public class DiffPoly {

    public static PolyTree diffTree(PolyTree father) {
        PolyTree newNode = new PolyTree();

        switch (father.getOp()) {
            case Operate.ADD:
            case Operate.SUB:
                newNode = opAddOrSub(father);
                break;
            case Operate.MUL:
                newNode = opMul(father);
                break;
            case Operate.SIN:
                newNode = opSin(father);
                break;
            case Operate.COS:
                newNode = opCos(father);
                break;
            case Operate.NOT:
                newNode = opNot(father);
                break;
            default:
                break;
        }

        return newNode;
    }

    private static PolyTree opAddOrSub(PolyTree father) {
        PolyTree newNode = new PolyTree();
        newNode.setOp(father.getOp());

        PolyTree newLeft;
        newLeft = diffTree(father.getLeftNode());
        newNode.setLeftNode(newLeft);

        PolyTree newRight;
        newRight = diffTree(father.getRightNode());
        newNode.setRightNode(newRight);

        return newNode;
    }

    private static PolyTree opMul(PolyTree father) {
        PolyTree newNode = new PolyTree();
        newNode.setOp(Operate.ADD);

        PolyTree newLeft = new PolyTree();
        newLeft.setOp(Operate.MUL);
        newLeft.setLeftNode(diffTree(father.getLeftNode()));
        newLeft.setRightNode(father.getRightNode());
        newNode.setLeftNode(newLeft);

        PolyTree newRight = new PolyTree();
        newRight.setOp(Operate.MUL);
        newRight.setLeftNode(father.getLeftNode());
        newRight.setRightNode(diffTree(father.getRightNode()));
        newNode.setRightNode(newRight);

        return newNode;
    }

    private static PolyTree opSin(PolyTree father) {
        return sinOrCos(father, Operate.SIN, Operate.COS);
    }

    private static PolyTree opCos(PolyTree father) {
        return sinOrCos(father, Operate.COS, Operate.SIN);
    }

    private static PolyTree sinOrCos(PolyTree father, String sin, String cos) {
        PolyTree newNode = new PolyTree();
        newNode.setOp(Operate.MUL);

        PolyTree newLeft = new PolyTree();
        newLeft.setOp(Operate.MUL);

        PolyTree leftLeft = new PolyTree();
        leftLeft.setOp(Operate.NOT);
        leftLeft.setFactor(father.getFactor().diff()[0]);
        newLeft.setLeftNode(leftLeft);

        PolyTree leftRigth = new PolyTree();
        leftRigth.setOp(sin);
        leftRigth.setFactor(father.getFactor().diff()[1]);
        leftRigth.setLeftNode(father.getLeftNode());
        newLeft.setRightNode(leftRigth);

        newNode.setLeftNode(newLeft);

        PolyTree newRight = new PolyTree();
        newRight.setOp(Operate.MUL);

        PolyTree rightLeft = new PolyTree();
        rightLeft.setOp(cos);
        rightLeft.setFactor(father.getFactor().diff()[2]);
        rightLeft.setLeftNode(father.getLeftNode());
        newRight.setLeftNode(rightLeft);

        PolyTree rightRight = new PolyTree();
        rightRight.setOp(Operate.NOT);
        rightRight = diffTree(father.getLeftNode());
        newRight.setRightNode(rightRight);

        newNode.setRightNode(newRight);

        return newNode;
    }

    private static PolyTree opNot(PolyTree father) {
        PolyTree newNode = new PolyTree();
        PolyTree newLeft = new PolyTree();
        PolyTree newRight = new PolyTree();

        if (father.isCons()) {
            newNode.setOp(Operate.NOT);
            newNode.setFactor(father.getFactor().diff()[0]);
        } else {
            newNode.setOp(Operate.MUL);

            newLeft.setOp(Operate.NOT);
            newLeft.setFactor(father.getFactor().diff()[0]);
            newNode.setLeftNode(newLeft);

            newRight.setOp(Operate.NOT);
            newRight.setFactor(father.getFactor().diff()[1]);
            newNode.setRightNode(newRight);
        }

        return newNode;
    }
}