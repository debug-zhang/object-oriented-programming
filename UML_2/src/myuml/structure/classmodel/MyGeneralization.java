package myuml.structure.classmodel;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import myuml.function.Find;
import myuml.function.UmlRule;

import java.util.HashMap;
import java.util.HashSet;

public class MyGeneralization {
    private HashMap<String, String> classMap;                // key = sonId
    private HashMap<String, HashSet<String>> interfaceMap;   // key = sonId

    public MyGeneralization() {
        this.classMap = new HashMap<>();
        this.interfaceMap = new HashMap<>();
    }

    public boolean contains(String id) {
        return containsClass(id) || containsInterface(id);
    }

    public boolean containsClass(String id) {
        return classMap.containsKey(id);
    }

    public boolean containsInterface(String id) {
        return interfaceMap.containsKey(id);
    }

    public void add(UmlElement element) {
        UmlGeneralization g = (UmlGeneralization) element;
        String sonId = g.getSource();
        String fatherId = g.getTarget();
        if (Find.containsInterfaceId(sonId)) {
            if (!containsInterface(sonId)) {
                interfaceMap.put(sonId, new HashSet<>());
            }
            if (interfaceMap.get(sonId).contains(fatherId)) {
                Find.getInterfaceById(sonId).setDupInherit(true);
                UmlRule.addUml009(sonId);
            } else {
                interfaceMap.get(sonId).add(fatherId);
            }
        } else {
            classMap.put(sonId, fatherId);
        }
    }

    public UmlElement getClassFather(String id) {
        return Find.getById(classMap.get(id));
    }

    public HashSet<String> getInterfaceFather(String id) {
        return interfaceMap.get(id);
    }
}
