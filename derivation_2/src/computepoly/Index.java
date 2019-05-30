package computepoly;

import java.math.BigInteger;
import java.util.Objects;

public class Index {
    private BigInteger powx;
    private BigInteger cos;
    private BigInteger sin;

    Index() {
        this.powx = BigInteger.ZERO;
        this.cos = BigInteger.ZERO;
        this.sin = BigInteger.ZERO;
    }

    Index(BigInteger powx, BigInteger cos, BigInteger sin) {
        this.powx = powx;
        this.cos = cos;
        this.sin = sin;
    }

    /*
    public boolean isCons() {
        return powx.equals(BigInteger.ZERO) & cos.equals(BigInteger.ZERO)
                & sin.equals(BigInteger.ZERO);
    }

    public void setPowx(BigInteger powx) {
        this.powx = powx;
    }

    public void setCos(BigInteger cos) {
        this.cos = cos;
    }

    public void setSin(BigInteger sin) {
        this.sin = sin;
    }
    */

    BigInteger getPowx() {
        return powx;
    }

    BigInteger getCos() {
        return cos;
    }

    BigInteger getSin() {
        return sin;
    }

    void addX(BigInteger x) {
        this.powx = this.powx.add(x);
    }

    void addCos(BigInteger cos) {
        this.cos = this.cos.add(cos);
    }

    void addSin(BigInteger sin) {
        this.sin = this.sin.add(sin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Index index = (Index) o;
        return Objects.equals(powx, index.powx) &&
                Objects.equals(cos, index.cos) &&
                Objects.equals(sin, index.sin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(powx, cos, sin);
    }
}
