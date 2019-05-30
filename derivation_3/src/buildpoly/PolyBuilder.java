package buildpoly;

import factor.Cos;
import factor.Sin;

import java.util.ArrayList;
import java.util.Stack;

public class PolyBuilder {
    private ArrayList<PolyTree> poly;
    private Stack<PolyTree> stack = new Stack<>();
    private ArrayList<PolyTree> suffix = new ArrayList<>();

    public PolyBuilder(ArrayList<PolyTree> poly) {
        this.poly = poly;
    }

    public void suffixBuilder() {
        for (int i = 0; i < poly.size(); i++) {
            PolyTree node = poly.get(i);
            switch (node.getOp()) {
                case Operate.ADD:
                case Operate.SUB:
                    addOrSub();
                    stack.push(node);
                    break;
                case Operate.MUL:
                    mul();
                    stack.push(node);
                    break;
                case Operate.SIN:
                case Operate.COS:
                case Operate.LEFT:
                    stack.push(node);
                    break;
                case Operate.RIGHT:
                    label:
                    while (true) {
                        Error.formatError(stack.empty());
                        PolyTree newNode = stack.pop();
                        switch (newNode.getOp()) {
                            case Operate.SIN:
                                newNode.setFactor(new
                                        Sin(node.getFactor().getIndex()));
                                suffix.add(newNode);
                                break label;
                            case Operate.COS:
                                newNode.setFactor(new
                                        Cos(node.getFactor().getIndex()));
                                suffix.add(newNode);
                                break label;
                            case Operate.LEFT:
                                break label;
                            default:
                                suffix.add(newNode);
                        }
                    }
                    break;
                case Operate.NOT:
                    suffix.add(node);
                    break;
                default:
                    Error.formatError(true);
            }
        }
        emptyStack();
    }

    public PolyTree polyTreeBuilder() {
        suffixBuilder();
        PolyTree newLeft;
        PolyTree newRight;
        for (int i = 0; i < suffix.size(); i++) {
            PolyTree node = suffix.get(i);
            switch (node.getOp()) {
                case Operate.ADD:
                case Operate.SUB:
                case Operate.MUL:
                    newRight = stack.pop();
                    node.setRightNode(newRight);
                    newLeft = stack.pop();
                    node.setLeftNode(newLeft);
                    stack.push(node);
                    break;
                case Operate.SIN:
                case Operate.COS:
                    newLeft = stack.pop();
                    node.setLeftNode(newLeft);
                    stack.push(node);
                    break;
                case Operate.NOT:
                    stack.push(node);
                    break;
                default:
                    break;
            }
        }
        return stack.pop();
    }

    public void emptyStack() {
        while (!stack.empty()) {
            PolyTree node = stack.pop();
            switch (node.getOp()) {
                case Operate.SIN:
                case Operate.COS:
                case Operate.RIGHT:
                    Error.formatError(true);
                    break;
                default:
                    suffix.add(node);
                    break;
            }
        }
    }

    public void addOrSub() {
        while (true) {
            if (stack.empty()) {
                break;
            } else {
                PolyTree newNode = stack.pop();
                if (newNode.getOp().equals(Operate.ADD) |
                        newNode.getOp().equals(Operate.SUB) |
                        newNode.getOp().equals(Operate.MUL)) {
                    suffix.add(newNode);

                } else {
                    stack.push(newNode);
                    break;
                }
            }
        }
    }

    public void mul() {
        while (true) {
            if (stack.empty()) {
                break;
            } else {
                PolyTree newNode = stack.pop();
                if (newNode.getOp().equals(Operate.MUL)) {
                    suffix.add(newNode);
                } else {
                    stack.push(newNode);
                    break;
                }
            }
        }
    }
}
