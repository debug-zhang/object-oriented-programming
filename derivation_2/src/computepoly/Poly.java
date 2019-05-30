package computepoly;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Poly {
    private HashMap<Index, Term> polyMap = new HashMap<>();

    public Poly(String[] poly) {
        for (String i : poly) {
            String[] factor = i.split("\\*");
            Index index = new Index();
            BigInteger cons = BigInteger.ONE;
            for (String j : factor) {
                if (j.isEmpty()) {
                    continue;
                }
                if (j.charAt(0) == '-') {
                    cons = cons.negate();
                    j = j.replaceFirst("-", "");
                }
                j = j.replaceAll("\\+", "");
                if (Pattern.matches("\\d+", j)) {
                    cons = cons.multiply(new BigInteger(j));
                } else if (Pattern.matches("x(\\^-?\\d+)?", j)) {
                    j = j.replaceAll("x(\\^)?", "");
                    if (j.isEmpty()) {
                        index.addX(BigInteger.ONE);
                    } else {
                        index.addX(new BigInteger(j));
                    }
                } else if (Pattern.matches("cos\\(x\\)(\\^-?\\d+)?", j)) {
                    j = j.replaceAll("cos\\(x\\)(\\^)?", "");
                    if (j.isEmpty()) {
                        index.addCos(BigInteger.ONE);
                    } else {
                        index.addCos(new BigInteger(j));
                    }
                } else if (Pattern.matches("sin\\(x\\)(\\^-?\\d+)?", j)) {
                    j = j.replaceAll("sin\\(x\\)(\\^)?", "");
                    if (j.isEmpty()) {
                        index.addSin(BigInteger.ONE);
                    } else {
                        index.addSin(new BigInteger(j));
                    }
                }
            }

            if (polyMap.containsKey(index)) {
                Term oldPloy = polyMap.get(index);
                Term newPoly = new Term(cons.add(oldPloy.getCons()), index);
                if (newPoly.getCons().equals(BigInteger.ZERO)) {
                    polyMap.remove(index);
                } else {
                    polyMap.put(index, newPoly);
                }
            } else {
                Term newPoly = new Term(cons, index);
                if (newPoly.getCons().equals(BigInteger.ZERO)) {
                    polyMap.remove(index);
                } else {
                    polyMap.put(index, newPoly);
                }
            }
        }
    }

    public void diff() {
        Term term;
        HashMap<Index, Term> diffPoly = new HashMap<>();
        for (Map.Entry<Index, Term> indexTermEntry : polyMap.entrySet()) {
            term = (Term) ((HashMap.Entry) indexTermEntry).getValue();
            term.diff(diffPoly);
        }
        polyMap = diffPoly;
    }

    public void print() {
        boolean isZero = true;
        for (Map.Entry<Index, Term> indexTermEntry : polyMap.entrySet()) {
            Term term = (Term) ((HashMap.Entry) indexTermEntry).getValue();
            isZero = term.print(!isZero);
        }
        if (isZero) {
            System.out.print(0);
        }
        System.out.println();
    }

    public void simplify() {
        HashMap<Index, Term> simMap = new HashMap<>();
        BigInteger two = BigInteger.valueOf(2);
        for (Map.Entry<Index, Term> termEntry1 : polyMap.entrySet()) {
            Term term1 = (Term) ((HashMap.Entry) termEntry1).getValue();
            Index index1 = term1.getIndex();
            BigInteger cons1 = term1.getCons();
            BigInteger powx1 = index1.getPowx();
            BigInteger sin1 = index1.getSin();
            BigInteger cos1 = index1.getCos();
            boolean simFlag;
            simFlag = false;
            for (Map.Entry<Index, Term> termEntry2 : polyMap.entrySet()) {
                Term term2 = (Term) ((HashMap.Entry) termEntry2).getValue();
                if (term2.getCons().equals(BigInteger.ZERO)) {
                    continue;
                }
                Index index2 = term2.getIndex();
                if (term1.getCons().equals(term2.getCons()) &
                        powx1.equals(index2.getPowx())) {
                    if (sin1.equals(index2.getSin().add(two)) &
                            cos1.equals(index2.getCos().subtract(two))) {
                        Index index;
                        index = new Index(powx1, cos1, sin1.subtract(two));
                        saveSimpTerm(cons1, simMap, index);
                    } else if (sin1.equals(index2.getSin().subtract(two)) &
                            cos1.equals(index2.getCos().add(two))) {
                        Index index;
                        index = new Index(powx1, cos1.subtract(two), sin1);
                        saveSimpTerm(cons1, simMap, index);
                    } else {
                        continue;
                    }
                    term1.setConsZero();
                    term2.setConsZero();
                    polyMap.put(index1, term1);
                    polyMap.put(index2, term2);
                    simFlag = true;
                }
            }
            if (!simFlag & !cons1.equals(BigInteger.ZERO)) {
                saveSimpTerm(cons1, simMap, index1);
            }
        }
        polyMap = simMap;
    }

    private void saveSimpTerm(BigInteger cons,
                              HashMap<Index, Term> simPoly, Index index) {
        if (simPoly.containsKey(index)) {
            Term old = simPoly.get(index);
            BigInteger newCons = old.getCons().add(cons);
            Term newTerm;
            newTerm = new Term(newCons, index);
            if (newTerm.getCons().equals(BigInteger.ZERO)) {
                simPoly.remove(index);
            } else {
                simPoly.put(index, newTerm);
            }
        } else {
            Term newTerm = new Term(cons, index);
            simPoly.put(index, newTerm);
        }
    }
}