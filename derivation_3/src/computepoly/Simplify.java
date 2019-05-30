package computepoly;

public class Simplify {
    public static String simpOp(String str) {
        String newStr = str.replaceAll("\\+\\+|--", "+");
        newStr = newStr.replaceAll("\\+-|-\\+", "-");
        newStr = newStr.replaceAll("\\+\\+|--", "+");
        newStr = newStr.replaceAll("\\+-|-\\+", "-");
        return newStr;
    }

    public static String simpFirst(String str) {
        String newStr = str.replaceAll("\\(-", "(0-");
        newStr = newStr.replaceAll("\\(\\+", "(");
        return newStr;
    }

    static String simpOne(String str) {
        String caret0 = "(sin\\(x\\)\\^0)|(cos\\(x\\)\\^0)";
        String newStr = str.replaceAll(caret0, "1");

        String caret1 = "\\(1\\*1\\)";
        newStr = newStr.replaceAll(caret1, "1");

        String caret2 = "\\(-1\\*1\\)";
        newStr = newStr.replaceAll(caret2, "-1");

        String caret3 = "\\(1\\*-1\\)";
        newStr = newStr.replaceAll(caret3, "-1");

        String caret4 = "\\(-1\\*-1\\)";
        newStr = newStr.replaceAll(caret4, "1");

        return newStr;
    }
}
