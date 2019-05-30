package computepoly;

public class Error {
    public static void formatError(boolean isError) {
        if (isError) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
    }
}
