package computepoly;

import java.util.regex.Pattern;

public class PolyChecker {
    private String str;

    public PolyChecker(String str) {
        this.str = str;
    }

    private void blankChecker(String line) {
        String wrong1 = ".*((\\d+\\s+\\d+)|" +
                "([+\\-]\\s*[+\\-]\\s*[+\\-]\\s+\\d+)|" +
                "(\\*\\s*[+\\-]\\s+\\d+)|" +
                "(s\\s+i\\s*n)|" +
                "(s\\s*i\\s+n)|" +
                "(c\\s+o\\s*s)|" +
                "(c\\s*o\\s+s)|" +
                "(\\^\\s*[+\\-]\\s+\\d+)).*";
        boolean isLegal1 = Pattern.matches(wrong1, line);
        str = line.replaceAll("[ \t]", "");
        String wrong2 = "\\s";
        boolean isLegal2 = Pattern.matches(wrong2, str);
        String wrong3 = "(\\(([^x]+)|(([^x]+)?x[^x]+)\\))";
        boolean isLegal3 = Pattern.matches(wrong3, str);
        Error.formatError(isLegal1 | isLegal2 | isLegal3 | str.isEmpty());
    }

    private void charChecker(String line) {
        String wrong = ".*[^0-9xcosin*+\\-()^].*";
        Error.formatError(Pattern.matches(wrong, line));
    }

    private void polyChecker(String line) {
        String pattern = "^([+-]?([+-]?" +
                "((([+-]?\\d+)|" +
                "(x(\\^[+-]?\\d+)?)|" +
                "(cos\\(x\\)(\\^[+-]?\\d+)?)|" +
                "(sin\\(x\\)(\\^[+-]?\\d+)?))\\*)*" +
                "(([+-]?\\d+)|" +
                "(x(\\^[+-]?\\d+)?)|" +
                "(cos\\(x\\)(\\^[+-]?\\d+)?)|" +
                "(sin\\(x\\)(\\^[+-]?\\d+)?))))";

        String newLine = line;
        String temp = "";
        while (!temp.equals(newLine)) {
            temp = newLine;
            newLine = newLine.replaceAll(pattern, "");
            if (!newLine.isEmpty()) {
                Error.formatError(newLine.charAt(0) != '+' &
                        newLine.charAt(0) != '-');
            }
        }

        Error.formatError(!newLine.isEmpty());
    }

    public String[] fomatCheck() {
        Error.formatError(str.isEmpty());
        blankChecker(str);
        charChecker(str);
        polyChecker(str);

        str = str.replaceAll("\\+\\+|--", "+");
        str = str.replaceAll("\\+-|-\\+", "-");
        str = str.replaceAll("\\+\\+|--", "+");
        str = str.replaceAll("\\+-|-\\+", "-");

        str = str.replaceAll("\\+", " +");
        str = str.replaceAll("-", " -");
        str = str.replaceAll("\\^\\s", "^");
        str = str.replaceAll("\\*\\s", "\\*");

        return str.split("[\\s]");
    }
}
