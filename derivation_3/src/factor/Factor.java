package factor;

import java.math.BigInteger;

public abstract class Factor {
    private BigInteger index;

    Factor() {
        this.index = BigInteger.ZERO;
    }

    Factor(BigInteger index) {
        this.index = index;
    }

    public BigInteger getIndex() {
        return index;
    }

    public abstract Factor clone();

    public abstract String toString();

    public abstract Factor[] diff();

}
