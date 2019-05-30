package railwaysystem;

public class Railway {
    public static final int trans = 121;
    public static final int price = 2;
    public static final int Us = 32;
    private static final int[] power = {1, 4, 16, 64, 256, 1024};

    private static int f(int x) {
        return (x % 5 + 5) % 5;
    }

    private static int h(int x) {
        return power[x];
    }

    public static int ue(int u, int v) {
        return h(Math.max(f(u), f(v)));
    }
}
