package computepoly;

import java.math.BigInteger;
import java.util.HashMap;

public class Term {
    private BigInteger cons;
    private Index index;

    /*
    public Term() {
        this.cons = BigInteger.ONE;
        this.index = new Index();
    }
    */

    Term(BigInteger cons, Index index) {
        this.cons = cons;
        this.index = index;
    }

    BigInteger getCons() {
        return cons;
    }

    /*
    public void setIndex(Index index) {
        this.index = index;
    }
    */

    void setConsZero() {
        this.cons = BigInteger.ZERO;
    }

    Index getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else if (!(obj instanceof Term)) {
            return false;
        } else {
            Term term = (Term) obj;
            return term.index.equals(this.index);
        }
    }

    /*
    public void add(Term term) {
        cons = cons.add(term.getCons());
    }
    */

    void diff(HashMap<Index, Term> diffPoly) {
        Index indexX = new Index(index.getPowx().subtract(BigInteger.ONE),
                index.getCos(), index.getSin());
        merge(diffPoly, indexX, cons.multiply(index.getPowx()));

        Index indexCos = new Index(index.getPowx(),
                index.getCos().subtract(BigInteger.ONE),
                index.getSin().add(BigInteger.ONE));
        merge(diffPoly, indexCos, cons.multiply(index.getCos().negate()));

        Index indexSin = new Index(index.getPowx(),
                index.getCos().add(BigInteger.ONE),
                index.getSin().subtract(BigInteger.ONE));
        merge(diffPoly, indexSin, cons.multiply(index.getSin()));
    }

    private void merge(HashMap<Index, Term> diffPoly,
                       Index index, BigInteger cons) {
        if (diffPoly.containsKey(index)) {
            Term oldPloy = diffPoly.get(index);
            Term newPoly = new Term(cons.add(oldPloy.getCons()), index);
            if (newPoly.getCons().equals(BigInteger.ZERO)) {
                diffPoly.remove(index);
            } else {
                diffPoly.put(index, newPoly);
            }
        } else {
            Term newPoly = new Term(cons, index);
            if (newPoly.getCons().equals(BigInteger.ZERO)) {
                diffPoly.remove(index);
            } else {
                diffPoly.put(index, newPoly);
            }
        }
    }

    private void printCons(BigInteger cons, boolean firstOut) {
        boolean isZero = cons.equals(BigInteger.ZERO);
        boolean isOne = cons.equals(BigInteger.ONE);
        boolean isNeOne = cons.equals(BigInteger.ONE.negate());
        boolean isNegate = cons.compareTo(BigInteger.ZERO) < 0;

        if (isNeOne) {
            System.out.print("-");
        } else if (isOne & firstOut) {
            System.out.print("+");
        } else if (!isZero & firstOut & !isNegate) {
            System.out.print("+" + cons);
        } else if (!isOne) {
            System.out.print(cons);
        }
    }

    private void printX(BigInteger index) {
        boolean isZero = index.equals(BigInteger.ZERO);
        boolean isOne = index.equals(BigInteger.ONE);

        if (isOne) {
            System.out.print("x");
        } else if (!isZero) {
            System.out.print("x^" + index);
        }
    }

    private void printCos(BigInteger index) {
        boolean isZero = index.equals(BigInteger.ZERO);
        boolean isOne = index.equals(BigInteger.ONE);

        if (isOne) {
            System.out.print("cos(x)");
        } else if (!isZero) {
            System.out.print("cos(x)^" + index);
        }
    }

    private void printSin(BigInteger index) {
        boolean isZero = index.equals(BigInteger.ZERO);
        boolean isOne = index.equals(BigInteger.ONE);

        if (isOne) {
            System.out.print("sin(x)");
        } else if (!isZero) {
            System.out.print("sin(x)^" + index);
        }
    }

    boolean print(boolean firstOut) {
        BigInteger powx = index.getPowx();
        BigInteger cos = index.getCos();
        BigInteger sin = index.getSin();

        boolean isPowxZero = powx.equals(BigInteger.ZERO);
        boolean isCosZero = cos.equals(BigInteger.ZERO);
        boolean isSinZero = sin.equals(BigInteger.ZERO);
        boolean isConsZero = cons.equals(BigInteger.ZERO);
        boolean isZero = true;

        if (!isConsZero) {
            if (isPowxZero & isCosZero & isSinZero) {
                if (!firstOut | cons.compareTo(BigInteger.ZERO) < 0) {
                    System.out.print(cons);
                } else {
                    System.out.print("+" + cons);
                }
            } else {
                printCons(cons, firstOut);
                if (!cons.equals(BigInteger.ONE.negate()) &
                        !cons.equals(BigInteger.ONE)) {
                    System.out.print("*");
                }
                if (isPowxZero & isCosZero & !isSinZero) {
                    printSin(sin);
                } else if (isPowxZero & !isCosZero & isSinZero) {
                    printCos(cos);
                } else if (!isPowxZero & isCosZero & isSinZero) {
                    printX(powx);
                } else if (isPowxZero & !isCosZero & !isSinZero) {
                    printCos(cos);
                    System.out.print("*");
                    printSin(sin);
                } else if (!isPowxZero & isCosZero & !isSinZero) {
                    printX(powx);
                    System.out.print("*");
                    printSin(sin);
                } else if (!isPowxZero & !isCosZero & isSinZero) {
                    printX(powx);
                    System.out.print("*");
                    printCos(cos);
                } else {
                    printX(powx);
                    System.out.print("*");
                    printCos(cos);
                    System.out.print("*");
                    printSin(sin);
                }
            }
            isZero = false;
        }
        return isZero;
    }
}


