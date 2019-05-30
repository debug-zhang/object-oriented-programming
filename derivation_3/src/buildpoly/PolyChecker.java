package buildpoly;

import computepoly.Simplify;
import factor.Cons;
import factor.Power;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyChecker {
    private Stack<String> opStack = new Stack<>();

    private String blankChecker(String line) {
        String wrong1 = ".*((\\d+\\s+\\d+)|" +
                "([+\\-]\\s*[+\\-]\\s*[+\\-]\\s+\\d+)|" +
                "(\\*\\s*[+\\-]\\s+\\d+)|" +
                "(\\^\\s*[+\\-]\\s+\\d+)|" +
                "(s\\s+i\\s*n)|" +
                "(s\\s*i\\s+n)|" +
                "(c\\s+o\\s*s)|" +
                "(c\\s*o\\s+s)).*";
        boolean isLegal1 = Pattern.matches(wrong1, line);

        String newLine;
        newLine = line.replaceAll("[ \t]", "");

        String wrong2 = "\\s";
        boolean isLegal2 = Pattern.matches(wrong2, newLine);

        boolean isLegal3 = newLine.isEmpty();

        Error.formatError(isLegal1 | isLegal2 | isLegal3);

        return newLine;
    }

    private void charChecker(String line) {
        String wrong = ".*[^0-9xcosin*+\\-()^]";
        Error.formatError(Pattern.matches(wrong, line));
    }

    private void indexNegateChecker(String line) {
        boolean isLegal = line.contains("^-");
        Error.formatError(isLegal);
    }

    private void opChecker(String line) {
        String wrong1 = ".*([+\\-]{3,}[^0-9]).*";
        boolean isLegal1 = Pattern.matches(wrong1, line);
        String wrong2 = ".*([+\\-]{4,}).*";
        boolean isLegal2 = Pattern.matches(wrong2, line);
        String wrong3 = ".*(\\d[xcs(]).*";
        boolean isLegal3 = Pattern.matches(wrong3, line);
        Error.formatError(isLegal1 | isLegal2 | isLegal3);
    }

    private ArrayList<PolyTree> termChecker(String line) {
        ArrayList<PolyTree> poly = new ArrayList<>();

        String str;
        if (line.charAt(0) == '+') {
            str = line.substring(1);
        } else if (line.charAt(0) == '-') {
            str = "0-" + line.substring(1);
        } else {
            str = line;
        }
        str = str.replaceAll("\\*", "*#");
        str = str.replaceAll("#\\(", "(");

        int i = 0;
        while (i < str.length()) {
            PolyTree newNode = new PolyTree();
            switch (str.charAt(i)) {
                case '+':
                    newNode.setOp(Operate.ADD);
                    i++;
                    break;
                case '-':
                    newNode.setOp(Operate.SUB);
                    i++;
                    break;
                case '*':
                    newNode.setOp(Operate.MUL);
                    i++;
                    break;
                case '(':
                    newNode.setOp(Operate.LEFT);
                    opStack.push(Operate.LEFT);
                    i++;
                    break;
                default:
                    if (str.charAt(i) == '#') { i++; }
                    String newPoly = str.substring(i);
                    Object[] obj = addPoly(newPoly, i);
                    newNode = (PolyTree) obj[0];
                    i = (Integer) obj[1];
            }
            poly.add(newNode);
        }
        Error.formatError(!opStack.empty());
        return poly;
    }

    private Object[] addPoly(String str, int oldI) {
        Pattern co = Pattern.compile("^([+\\-]?\\d+)");
        Matcher cons = co.matcher(str);
        Pattern pow = Pattern.compile("^(x(\\^(\\+)?\\d+)?)");
        Matcher power = pow.matcher(str);
        Pattern sinLe = Pattern.compile("^sin\\(");
        Matcher sinLeft = sinLe.matcher(str);
        Pattern cosLe = Pattern.compile("^cos\\(");
        Matcher cosLeft = cosLe.matcher(str);
        Pattern rig = Pattern.compile("^(\\)(\\^[+-]?\\d+)?)");
        Matcher right = rig.matcher(str);

        PolyTree newNode = new PolyTree();
        int i = oldI;
        if (cons.find()) {
            newNode.setOp(Operate.NOT);
            Cons acons = new Cons(new BigInteger(cons.group()));
            newNode.setFactor(acons);
            i += cons.end();
        } else if (power.find()) {
            newNode.setOp(Operate.NOT);
            if (power.group().length() == 1) {
                Power apower = new Power(BigInteger.ONE);
                newNode.setFactor(apower);
            } else {
                newNode = splitCaret(power, newNode);
            }
            i += power.group().length();
        } else if (sinLeft.find()) {
            newNode.setOp(Operate.SIN);
            opStack.push(Operate.SIN);
            i += sinLeft.end();
        } else if (cosLeft.find()) {
            newNode.setOp(Operate.COS);
            opStack.push(Operate.COS);
            i += cosLeft.end();
        } else if (right.find()) {
            if (right.group().length() == 1) {
                newNode.setOp(Operate.RIGHT);
                opStack.pop();
                Power apower = new Power(BigInteger.valueOf(1));
                newNode.setFactor(apower);
            } else {
                Error.formatError(opStack.pop().equals(Operate.LEFT));
                newNode.setOp(Operate.RIGHT);
                newNode = splitCaret(right, newNode);
            }
            i += right.group().length();
        } else {
            Error.formatError(true);
        }
        return new Object[]{newNode, i};
    }

    private PolyTree splitCaret(Matcher matcher, PolyTree newNode) {
        String ind = matcher.group().substring(2);
        int index = Integer.parseInt(ind);
        Error.formatError(index <= 0 |
                index > 10000);
        Power apower = new Power(BigInteger.valueOf(index));
        newNode.setFactor(apower);
        return newNode;
    }

    private void factorChecker(String line) {
        String wrong1 = ".*(sin\\(\\d+[+\\-*].*\\)).*";
        boolean isLegal1 = Pattern.matches(wrong1, line);
        String wrong2 = ".*(cos\\(\\d+[+\\-*].*\\)).*";
        boolean isLegal2 = Pattern.matches(wrong2, line);
        String wrong3 = ".*(sin\\([+\\-][^0-9].*\\)).*";
        boolean isLegal3 = Pattern.matches(wrong3, line);
        String wrong4 = ".*(cos\\([+\\-][^0-9].*\\)).*";
        boolean isLegal4 = Pattern.matches(wrong4, line);
        Error.formatError(isLegal1 | isLegal2 | isLegal3 | isLegal4);

        Pattern tri1 = Pattern.compile("^((sin)|(cos))\\(\\(.+\\)\\).*");
        Matcher triMat1;
        Pattern tri2 = Pattern.compile("^((sin)|(cos))\\([+\\-]?\\d+\\).*");
        Matcher triMat2;
        Pattern tri3 =
                Pattern.compile("^((sin)|(cos))\\(x(\\^(\\+)?\\d+)?\\).*");
        Matcher triMat3;
        Pattern tri4 =
                Pattern.compile("^((sin)|(cos))\\(((sin)|(cos)).*\\).*");
        Matcher triMat4;
        String str;
        for (int i = 0; i < line.length(); i++) {
            if ((line.charAt(i) == 's' || line.charAt(i) == 'c') &&
                    i < line.length() - 1) {
                if (line.charAt(i + 1) == 'i' || line.charAt(i) == 'o') {
                    str = line.substring(i);
                    triMat1 = tri1.matcher(str);
                    triMat2 = tri2.matcher(str);
                    triMat3 = tri3.matcher(str);
                    triMat4 = tri4.matcher(str);
                    isLegal1 = triMat1.find();
                    isLegal2 = triMat2.find();
                    isLegal3 = triMat3.find();
                    isLegal4 = triMat4.find();
                    boolean isLegal = isLegal1 | isLegal2 | isLegal3 | isLegal4;
                    Error.formatError(!isLegal);
                }
            }
        }
    }

    public ArrayList<PolyTree> fomatCheck(String str) {
        Error.formatError(str.isEmpty());

        String newStr = blankChecker(str);
        charChecker(newStr);
        indexNegateChecker(newStr);
        opChecker(newStr);
        newStr = Simplify.simpOp(newStr);
        factorChecker(newStr);

        newStr = Simplify.simpFirst(newStr);
        ArrayList<PolyTree> rightPoly;
        rightPoly = termChecker(newStr);
        return rightPoly;
    }
}
