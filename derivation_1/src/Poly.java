import java.math.BigInteger;

public class Poly {
    private BigInteger term;
    private BigInteger deg;
    private boolean isCons;
    private boolean isRepeat;

    public BigInteger getDeg() {
        return deg;
    }

    public void addDeg(BigInteger deg) {
        this.deg = this.deg.add(deg);
    }

    public BigInteger getTerm() {
        return term;
    }

    public void addTerm(BigInteger term) {
        this.term = this.term.add(term);
    }

    public boolean isCons() {
        return isCons;
    }

    public void setCons(boolean cons) {
        isCons = cons;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public Poly(String term, String deg) {
        this.term = new BigInteger(term);
        this.deg = new BigInteger(deg);
        isCons = false;
        isRepeat = false;
    }

    public void diff() {
        if (term.compareTo(BigInteger.ZERO) != 0
                && deg.compareTo(BigInteger.ZERO) != 0) {
            term = term.multiply(deg);
            deg = deg.subtract(BigInteger.ONE);
            if (deg.compareTo(BigInteger.ZERO) == 0) {
                isCons = true;
            } else {
                isCons = false;
            }
        } else {
            term = BigInteger.ZERO;
            deg = BigInteger.ZERO;
            isCons = true;
            isRepeat = true;
        }
    }
}
