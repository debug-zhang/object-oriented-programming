import computepoly.Error;
import computepoly.Poly;
import computepoly.PolyChecker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        if (input.hasNext()) {
            String newPoly = input.nextLine();
            PolyChecker checker = new PolyChecker(newPoly);
            String[] rightPoly = checker.fomatCheck();
            Poly poly = new Poly(rightPoly);
            poly.diff();
            poly.simplify();
            poly.simplify();
            poly.print();
        } else {
            computepoly.Error.formatError(true);
        }
    }
}
