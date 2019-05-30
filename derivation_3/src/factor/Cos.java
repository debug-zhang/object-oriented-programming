package factor;

import java.math.BigInteger;

public class Cos extends Factor {

    public Cos(BigInteger index) {
        super(index);
    }

    @Override
    public Factor clone() {
        return new Cos(this.getIndex());
    }

    @Override
    public String toString() {
        return "cos(";

    }

    @Override
    public Factor[] diff() {
        Cos acos = new Cos(this.getIndex().subtract(BigInteger.ONE));
        Sin asin = new Sin(BigInteger.ONE);
        Cons acons = new Cons(this.getIndex().negate());
        return new Factor[]{acons, acos, asin};
    }
}