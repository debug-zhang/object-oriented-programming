import buildpoly.Error;
import buildpoly.PolyBuilder;
import buildpoly.PolyChecker;
import buildpoly.PolyTree;
import computepoly.DiffPoly;
import computepoly.PrintPoly;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            String readLine = input.nextLine();
            PolyChecker polyChecker = new PolyChecker();
            ArrayList<PolyTree> poly = polyChecker.fomatCheck(readLine);
            PolyBuilder polyBuilder = new PolyBuilder(poly);
            PolyTree root = polyBuilder.polyTreeBuilder();
            PolyTree newRoot = DiffPoly.diffTree(root);
            System.out.println(PrintPoly.printPoly(newRoot));
        } catch (Exception e) {
            Error.formatError(true);
        }
    }
}
