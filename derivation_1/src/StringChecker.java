import java.util.regex.Pattern;

public class StringChecker {
    private String stringLine;

    public StringChecker(String input) {
        stringLine = input;
    }

    private boolean spaceChecker(String str) {
        String space1 = ".*(\\d+\\s+\\d+).*";
        boolean isLegal1 = Pattern.matches(space1, str);
        String space2 = ".*[+\\-^]\\s*[+\\-]\\s+\\d+.*";
        boolean isLegal2 = Pattern.matches(space2, str);
        return isLegal1 | isLegal2;
    }

    private boolean opChecker(String str) {
        String char1 = ".*([+\\-]{3,}|[*^]{2,}|[+\\-][*^]).*";
        boolean isLegal1 = Pattern.matches(char1, str);
        String char2 = ".*(\\*[+\\-]|\\^[+\\-]{2,}).*";
        boolean isLegal2 = Pattern.matches(char2, str);
        String char3 = ".*(([x0-9]*\\*[^x])|(x\\^[+\\-]*x)).*";
        boolean isLegal3 = Pattern.matches(char3, str);
        String char4 = "(.*[+\\-*^]$)|([*^].*)";
        boolean isLegal4 = Pattern.matches(char4, str);
        return isLegal1 | isLegal2 | isLegal3 | isLegal4;
    }

    private boolean charChecker(String str) {
        boolean isLegal1 = Pattern.matches(".*[^\\dx*+\\-^]+.*", str);
        boolean isLegal2 = Pattern.matches(".*((\\dx)|(x\\d)).*", str);
        boolean isLegal3 = Pattern.matches(".*(x{2,}).*", str);
        boolean isLegal4 =
                Pattern.matches(".*((\\*\\d)|(x\\*)).*", str);
        boolean isLegal5 =
                Pattern.matches(".*((\\d\\^)|(\\^x)).*", str);
        return isLegal1 | isLegal2 | isLegal3 | isLegal4 | isLegal5;
    }

    public String[] checkPoly() {
        if (spaceChecker(stringLine)) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        stringLine = stringLine.replaceAll("[ \t]", "");
        if (stringLine.isEmpty()) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }

        if (opChecker(stringLine)) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        stringLine =
                stringLine.replaceAll("\\+\\+|--", "+");
        stringLine =
                stringLine.replaceAll("\\+-|-\\+", "-");

        if (charChecker(stringLine)) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }

        if (stringLine.charAt(0) == 'x') {
            stringLine = "1*x" + stringLine.substring(1);
        }
        stringLine = stringLine.replaceAll("\\+x", "+1*x");
        stringLine = stringLine.replaceAll("-x", "-1*x");
        stringLine = stringLine.replaceAll("x\\+", "x^1+");
        stringLine = stringLine.replaceAll("x-", "x^1-");

        String temp = stringLine.substring(stringLine.length() - 1);
        if (temp.equals("x")) {
            stringLine = stringLine + "^1";
        }

        stringLine = stringLine.replace("+", " +");
        stringLine = stringLine.replace("-", " -");
        stringLine = stringLine.replace("^ +", "^+");
        stringLine = stringLine.replace("^ -", "^-");

        return stringLine.split("[\\s]");
    }
}