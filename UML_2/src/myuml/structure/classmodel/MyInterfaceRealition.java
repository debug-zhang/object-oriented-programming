package myuml.structure.classmodel;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import myuml.function.Find;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyInterfaceRealition {
    private HashMap<String, HashSet<String>> realiIdSetMap;
    private HashMap<String, ArrayList<String>> realiIdListMap;
    private HashMap<String, HashSet<UmlInterface>> realiSetMap;

    public MyInterfaceRealition() {
        realiIdSetMap = new HashMap<>();
        realiIdListMap = new HashMap<>();
        realiSetMap = new HashMap<>();
    }

    public boolean contains(String id) {
        return realiIdSetMap.containsKey(id);
    }

    public void add(UmlElement uml) {
        UmlInterfaceRealization r = (UmlInterfaceRealization) uml;
        if (!realiIdSetMap.containsKey(r.getSource())) {
            realiIdSetMap.put(r.getSource(), new HashSet<>());
            realiIdListMap.put(r.getSource(), new ArrayList<>());
            realiSetMap.put(r.getSource(), new HashSet<>());
        }
        realiIdSetMap.get(r.getSource()).add(r.getTarget());
        realiIdListMap.get(r.getSource()).add(r.getTarget());
        realiSetMap.get(r.getSource()).add(
                (UmlInterface) Find.getById(r.getTarget()));
    }

    public HashSet<String> getIdSet(String id) {
        return realiIdSetMap.get(id);
    }

    public HashSet<UmlInterface> getInterfaceSet(String id) {
        return realiSetMap.get(id);
    }

    public ArrayList<String> getRealiIdList(String id) {
        return realiIdListMap.get(id);
    }
}
