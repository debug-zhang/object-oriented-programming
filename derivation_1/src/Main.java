import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private ArrayList<Poly> polyList = new ArrayList<>();
    private static final BigInteger MINUXONE = BigInteger.valueOf(-1);

    private void parsePoly(String[] polyArr) {
        for (String str : polyArr) {
            if (str.isEmpty()) {
                continue;
            } else if (!Pattern.matches(".*x\\^.*", str)) {
                polyList.add(new Poly(str, "0"));
            } else {
                String[] temp = str.split("\\*x\\^");
                polyList.add(new Poly(temp[0], temp[1]));
            }
        }
    }

    private void computePoly() {
        for (int i = 0; i < polyList.size(); i++) {
            for (int j = i + 1; j < polyList.size(); j++) {
                if (polyList.get(i).getDeg().equals(polyList.get(j).getDeg())
                        && !polyList.get(j).isRepeat()) {
                    polyList.get(i).addTerm(polyList.get(j).getTerm());
                    polyList.get(j).setRepeat(true);
                }
            }
            polyList.get(i).diff();
        }
    }

    private void output() {
        BigInteger constant = BigInteger.ZERO;
        boolean firstOut = true;
        for (Poly p : polyList) {
            if (p.isRepeat()) {
                continue;
            } else if (p.isCons()) {
                constant = p.getTerm();
            } else if (firstOut) {
                if (p.getTerm().compareTo(BigInteger.ONE) == 0) {
                    System.out.print("x");
                } else if (p.getTerm().compareTo(MINUXONE) == 0) {
                    System.out.print("-x");
                } else if (p.getTerm().compareTo(BigInteger.ZERO) > 0) {
                    System.out.print(p.getTerm() + "*x");
                } else {
                    System.out.print(p.getTerm() + "*x");
                }
                firstOut = false;
            } else if (p.getTerm().compareTo(BigInteger.ONE) == 0) {
                System.out.print("+x");
            } else if (p.getTerm().compareTo(MINUXONE) == 0) {
                System.out.print("-x");
            } else if (p.getTerm().compareTo(BigInteger.ZERO) > 0) {
                System.out.print("+" + p.getTerm() + "*x");
            } else {
                System.out.print(p.getTerm() + "*x");
            }

            if (p.getDeg().compareTo(BigInteger.ONE) != 0
                    && p.getDeg().compareTo(BigInteger.ZERO) != 0) {
                System.out.print("^" + p.getDeg());
            }
        }
        if (constant.compareTo(BigInteger.ZERO) >= 0) {
            if (firstOut) {
                System.out.println(constant);
            } else if (constant.compareTo(BigInteger.ZERO) > 0) {
                System.out.println("+" + constant);
            }
        } else if (constant.compareTo(BigInteger.ZERO) < 0) {
            System.out.println(constant);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String readLine = input.nextLine();

        StringChecker stringChecker = new StringChecker(readLine);
        String[] polyArr = stringChecker.checkPoly();

        Main comPoly = new Main();
        comPoly.parsePoly(polyArr);
        comPoly.computePoly();
        comPoly.output();
    }
}
