package factor;

import java.math.BigInteger;

public class Power extends Factor {

    public Power(BigInteger index) {
        super(index);
    }

    @Override
    public Factor clone() {
        return new Power(this.getIndex());
    }

    @Override
    public String toString() {
        if (getIndex().equals(BigInteger.ZERO)) {
            return String.valueOf(1);
        } else if (getIndex().equals(BigInteger.ONE)) {
            return "x";
        } else {
            return "x^" + getIndex();
        }
    }

    @Override
    public Factor[] diff() {
        Power apower = new Power(this.getIndex().subtract(BigInteger.ONE));
        Cons acons = new Cons(this.getIndex());
        return new Factor[]{acons, apower};
    }
}
