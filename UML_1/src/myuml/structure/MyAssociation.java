package myuml.structure;

import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlElement;
import myuml.function.Find;

import java.util.HashMap;
import java.util.HashSet;

public class MyAssociation {
    private HashMap<String, HashSet<UmlElement>> associationEnd;
    private HashMap<String, HashSet<String>> associationClass;

    public MyAssociation() {
        this.associationEnd = new HashMap<>();
        this.associationClass = new HashMap<>();
    }

    public boolean contains(String id) {
        return associationEnd.containsKey(id);
    }

    public boolean containsClass(String id) {
        return associationClass.containsKey(id);
    }

    public void add(UmlElement element) {
        UmlAssociation a = (UmlAssociation) element;
        String id1 = Find.getIdByEndId(a.getEnd1());
        String id2 = Find.getIdByEndId(a.getEnd2());
        if (!contains(id1)) {
            associationEnd.put(id1, new HashSet<>());
        }
        associationEnd.get(id1).add(Find.getById(a.getEnd2()));
        if (!contains(id2)) {
            associationEnd.put(id2, new HashSet<>());
        }
        associationEnd.get(id2).add(Find.getById(a.getEnd1()));
        if (id1.equals(id2)) {
            if (Find.containsClassId(id1)) {
                if (!containsClass(id1)) {
                    associationClass.put(id1, new HashSet<>());
                }
                associationClass.get(id1).add(id1);
            }
        } else {
            if (Find.containsClassId(id1) && Find.containsClassId(id2)) {
                if (!containsClass(id1)) {
                    associationClass.put(id1, new HashSet<>());
                }
                associationClass.get(id1).add(id2);
                if (!containsClass(id2)) {
                    associationClass.put(id2, new HashSet<>());
                }
                associationClass.get(id2).add(id1);
            }
        }
    }

    public int getEndSize(String id) {
        return associationEnd.get(id).size();
    }

    public HashSet<String> getClassIdSet(String id) {
        return associationClass.get(id);
    }
}
