package myuml.structure.classmodel;

import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;
import myuml.function.Find;
import myuml.function.Relation;
import myuml.function.UmlRule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MyInterface {
    private UmlInterface umlInterface;
    private HashMap<String, UmlAttribute> attributeNameMap;
    private HashMap<String, MyOperation> operationIdMap;
    private HashSet<String> fatherSet;
    private boolean isDupInherit;

    public MyInterface(UmlElement umlInterface) {
        this.umlInterface = (UmlInterface) umlInterface;
        attributeNameMap = new HashMap<>();
        operationIdMap = new HashMap<>();
        fatherSet = new HashSet<>();
        isDupInherit = false;
    }

    public void addAttributeMap(UmlElement attribute) {
        attributeNameMap.put(attribute.getName(), (UmlAttribute) attribute);
    }

    public void addOperation(MyOperation operation) {
        operationIdMap.put(operation.getId(), operation);
    }

    public String getName() {
        return umlInterface.getName();
    }

    public String getId() {
        return umlInterface.getId();
    }

    public boolean isDupInherit() {
        return isDupInherit;
    }

    public void setDupInherit(boolean dupInherit) {
        isDupInherit = dupInherit;
    }

    public HashSet<String> getInterfaceIdSet() {
        if (fatherSet.isEmpty()) {
            Queue<String> queue = new LinkedList<>();
            queue.offer(getId());
            while (!queue.isEmpty()) {
                String id = queue.poll();
                if (Relation.hasGeneralization(id)) {
                    for (String tempId : Relation.getInterfaceFather(id)) {
                        queue.offer(tempId);
                        if (!isDupInherit() && fatherSet.contains(tempId)) {
                            UmlRule.addUml009(getId());
                            setDupInherit(true);
                        }
                        fatherSet.add(tempId);
                    }
                }
            }
        }
        checkFatherDupInherit();
        return fatherSet;
    }

    private void checkFatherDupInherit() {
        if (!isDupInherit()) {
            for (String id : fatherSet) {
                if (Find.getInterfaceById(id).isDupInherit()) {
                    UmlRule.addUml009(getId());
                    setDupInherit(true);
                    break;
                }
            }
        }
    }
}
