package factor;

import java.math.BigInteger;

public class Cons extends Factor {
    private BigInteger coef;

    public Cons(BigInteger coef) {
        super(BigInteger.ZERO);
        this.coef = coef;
    }

    @Override
    public Factor clone() {
        return new Cons(this.coef);
    }

    @Override
    public String toString() {
        return String.valueOf(coef);
    }

    @Override
    public Factor[] diff() {
        return new Factor[]{new Cons(BigInteger.ZERO)};
    }
}
