package computepoly;

import buildpoly.Operate;
import buildpoly.PolyTree;
import factor.Factor;

import java.math.BigInteger;

public class PrintPoly {
    public static String printPoly(PolyTree father) {
        StringBuilder strBuilder = new StringBuilder();
        Factor temp = father.getFactor();

        switch (father.getOp()) {
            case Operate.ADD:
            case Operate.SUB:
            case Operate.MUL:
                strBuilder.append(Operate.LEFT);
                strBuilder.append(printPoly(father.getLeftNode()));
                strBuilder.append(father.getOp());
                strBuilder.append(printPoly(father.getRightNode()));
                strBuilder.append(Operate.RIGHT);
                String simpStr;
                simpStr = Simplify.simpOp(strBuilder.toString());
                simpStr = Simplify.simpOne(simpStr);
                return simpStr;

            case Operate.SIN:
            case Operate.COS:
                strBuilder.append(temp.toString());
                strBuilder.append(printPoly(father.getLeftNode()));
                strBuilder.append(Operate.RIGHT);
                if (!temp.getIndex().equals(BigInteger.ONE)) {
                    strBuilder.append(Operate.CARET);
                    strBuilder.append(temp.getIndex());
                }
                simpStr = Simplify.simpOp(strBuilder.toString());
                simpStr = Simplify.simpOne(simpStr);
                return simpStr;

            case Operate.NOT:
                strBuilder.append(temp.toString());
                return strBuilder.toString();

            default:
                return null;
        }
    }
}
