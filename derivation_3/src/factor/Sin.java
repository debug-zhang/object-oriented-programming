package factor;

import java.math.BigInteger;

public class Sin extends Factor {

    public Sin(BigInteger index) {
        super(index);
    }

    @Override
    public Factor clone() {
        return new Sin(this.getIndex());
    }

    @Override
    public String toString() {
        return "sin(";
    }

    @Override
    public Factor[] diff() {
        Sin asin = new Sin(this.getIndex().subtract(BigInteger.ONE));
        Cos acos = new Cos(BigInteger.ONE);
        Cons acons = new Cons(this.getIndex());
        return new Factor[]{acons, asin, acos};
    }
}
